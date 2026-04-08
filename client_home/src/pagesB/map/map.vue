<template>
  <view :style="{ paddingTop: vuex_custom_bar_height + 'px', paddingBottom: vuex_safe_area_bottom + 'px' }">
    <tn-nav-bar>路线地图</tn-nav-bar>
    <view class="map-box">
      <map
        id="routeMap"
        :latitude="latitude"
        :longitude="longitude"
        :markers="markers"
        :polyline="polyline"
        :include-points="includePoints"
        scale="12"
        style="width: 100%; height: 100vh;"
      >
      </map>
      
      <view class="info-card" v-if="startAddress && endAddress">
        <view class="card-header">
           <text class="movie-name">{{ movieName || '路线详情' }}</text>
        </view>
        <view class="route-info">
             <view class="route-line" v-if="startAddress">
                 <view class="dot start"></view>
                 <text class="address">起点位置: {{ startAddress }}</text>
             </view>
             <view class="route-line" v-if="currentAddress">
                 <view class="dot current"></view>
                 <text class="address">当前位置: {{ currentAddress }}</text>
             </view>
             <view class="route-line" v-if="endAddress">
                 <view class="dot end"></view>
                 <text class="address">终点位置: {{ endAddress }}</text>
             </view>
         </view>
         <view class="stats">
             <view class="stat-item" v-if="distance">
                 <text class="num">{{ distance }}</text>
                 <text class="unit">路线距离</text>
             </view>
              <view class="stat-item" v-if="duration">
                 <text class="num">{{ duration }}</text>
                 <text class="unit">驾车耗时</text>
             </view>
             <view class="stat-item" v-if="walking_duration">
                <text class="num">{{ walking_duration }}</text>
                <text class="unit">步行耗时</text>
            </view>
         </view>
       </view>
    </view>
  </view>
</template>

<script>
const AMAP_KEY = 'bcafb11917e1c0c24268fa0e3c69aa5b';

export default {
  data() {
    return {
      vuex_custom_bar_height: this.$store.state.custom_bar_height,
      vuex_safe_area_bottom: this.$store.state.safe_area_bottom,
      latitude: 22.556481,
      longitude: 113.946782,
      markers: [],
      polyline: [],
      includePoints: [],
      startAddress: '',
      endAddress: '',
      movieName: '',
       distance: '',
       duration: '',
       walking_duration: '',
       currentAddress: ''
     };
   },
  onLoad(options) {
      this.startAddress = decodeURIComponent(options.start);
      this.endAddress = decodeURIComponent(options.end);
      this.movieName = decodeURIComponent(options.name);
      this.currentAddress = decodeURIComponent(options.location_address);
      this.initMap();
  },
  methods: {
    async initMap() {
        if(!this.startAddress || !this.endAddress) return;
        
        try {
            const startLoc = await this.getGeo(this.startAddress);
            const endLoc = await this.getGeo(this.endAddress);
            
            if(startLoc && endLoc) {
                // Set markers
                this.markers = [
                    {
                        id: 0,
                        latitude: startLoc.lat,
                        longitude: startLoc.lng,
                        width: 30,
                        height: 30,
                        iconPath: '/static/u4808.svg',
                        // callout: { content: '起点: ' + this.startAddress, display: 'ALWAYS', padding: 10, borderRadius: 5 }
                    },
                    {
                        id: 1,
                        latitude: endLoc.lat,
                        longitude: endLoc.lng,
                        width: 30,
                        height: 30,
                        iconPath: '/static/u4807.svg',
                        // callout: { content: '终点: ' + this.endAddress, display: 'ALWAYS', padding: 10, borderRadius: 5 }
                    }
                ];
                
                this.includePoints = [
                    { latitude: startLoc.lat, longitude: startLoc.lng },
                    { latitude: endLoc.lat, longitude: endLoc.lng }
                ];

                // 处理当前位置 Marker
                if (this.currentAddress) {
                    const currentLoc = await this.getGeo(this.currentAddress);
                    if (currentLoc) {
                        this.markers.push({
                            id: 2,
                            latitude: currentLoc.lat,
                            longitude: currentLoc.lng,
                            width: 30,
                            height: 30,
                            iconPath: '/static/current.png',
                            // callout: { content: '当前位置: ' + this.currentAddress, display: 'ALWAYS', padding: 10, borderRadius: 5, bgColor: '#ffaa00' }
                        });
                        // this.includePoints.push({ latitude: currentLoc.lat, longitude: currentLoc.lng });
                    }
                }
                
                // Center map
                this.latitude = (startLoc.lat + endLoc.lat) / 2;
                this.longitude = (startLoc.lng + endLoc.lng) / 2;
                
                // Get Route
                this.getRoute(startLoc, endLoc);
            }
        } catch (e) {
            console.error('Map initialization error:', e);
            uni.showToast({ title: '地址解析失败', icon: 'none' });
        }
    },
    getGeo(address) {
        return new Promise((resolve, reject) => {
            uni.request({
                url: 'https://restapi.amap.com/v3/geocode/geo',
                data: {
                    address: address,
                    key: AMAP_KEY
                },
                success: (res) => {
                    if(res.data.status === '1' && res.data.geocodes.length > 0) {
                        const location = res.data.geocodes[0].location.split(',');
                        resolve({ lng: parseFloat(location[0]), lat: parseFloat(location[1]) });
                    } else {
                        // Attempt to recover or just resolve null? 
                        // If strict, reject. But let's log and reject.
                        console.warn('Geo failed for', address, res.data);
                        resolve(null); 
                    }
                },
                fail: (err) => {
                    console.error('Geo request failed', err);
                    reject(err);
                }
            })
        })
    },
    getRoute(start, end) {
         uni.request({
            url: 'https://restapi.amap.com/v3/direction/driving',
            data: {
                origin: `${start.lng},${start.lat}`,
                destination: `${end.lng},${end.lat}`,
                key: AMAP_KEY
            },
            success: (res) => {
                if(res.data.status === '1' && res.data.route.paths.length > 0) {
                    const path = res.data.route.paths[0];
                    let dist = path.distance / 1000;
                    this.distance = dist.toFixed(1) + '公里';
                    
                    let dur = Math.ceil(path.duration / 60);
                    if (dur > 60) {
                        this.duration = '约' + Math.floor(dur / 60) + '小时' + (dur % 60) + '分钟';
                    } else {
                        this.duration = '约' + dur + '分钟';
                    }
                    
                    // Parse steps for polyline
                    const points = [];
                    path.steps.forEach(step => {
                        const polyline = step.polyline.split(';');
                        polyline.forEach(p => {
                            const [lng, lat] = p.split(',');
                            points.push({ latitude: parseFloat(lat), longitude: parseFloat(lng) });
                        });
                    });
                    
                    this.polyline = [{
                        points: points,
                        color: '#007AFF',
                        width: 6,
                        arrowLine: true
                    }];
                }
            }
         });

         // Walking route
         uni.request({
            url: 'https://restapi.amap.com/v3/direction/walking',
            data: {
                origin: `${start.lng},${start.lat}`,
                destination: `${end.lng},${end.lat}`,
                key: AMAP_KEY
            },
            success: (res) => {
                if(res.data.status === '1' && res.data.route.paths.length > 0) {
                    const path = res.data.route.paths[0];
                    let dur = Math.ceil(path.duration / 60);
                    if (dur > 60) {
                        this.walking_duration = '约' + Math.floor(dur / 60) + '小时' + (dur % 60) + '分钟';
                    } else {
                        this.walking_duration = '约' + dur + '分钟';
                    }
                }
            }
         })
    }
  }
};
</script>

<style scoped lang="scss">
.map-box {
    position: relative;
    width: 100%;
    height: 100vh;
}

.info-card {
    position: absolute;
    bottom: 40px;
    left: 20px;
    right: 20px;
    background: #fff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 4px 16px rgba(0,0,0,0.1);
    z-index: 100;
}

.card-header {
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.movie-name {
    font-size: 18px;
    font-weight: bold;
    color: #333;
}

.route-info {
    margin-bottom: 15px;
}

.route-line {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.dot {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    margin-right: 10px;
    flex-shrink: 0;
}

.dot.start {
    background: #00bf00;
}

.dot.current {
    background: #ffaa00;
}

.dot.end {
    background: #ff0000;
}

.address {
    font-size: 14px;
    color: #666;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.stats {
    display: flex;
    justify-content: space-around;
    background: #f8f8f8;
    padding: 10px;
    border-radius: 8px;
}

.stat-item {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.num {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    margin-bottom: 4px;
}

.unit {
    font-size: 12px;
    color: #999;
}
</style>
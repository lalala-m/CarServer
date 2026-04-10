#!/bin/bash
# WSL Redis 启动脚本
# 使用方法：在WSL终端中运行: bash redis_start.sh

# 检查redis-server是否安装
if ! command -v redis-server &> /dev/null; then
    echo "[错误] redis-server 未安装"
    echo "安装命令: sudo apt-get install redis-server"
    exit 1
fi

# 启动Redis（使用默认端口6379）
echo "正在启动Redis..."
redis-server --daemonize yes --port 6379

# 等待一下确保启动成功
sleep 2

# 测试连接
if redis-cli ping > /dev/null 2>&1; then
    echo "[成功] Redis已启动并运行在端口 6379"
    redis-cli info | grep tcp_port
else
    echo "[错误] Redis启动失败"
    exit 1
fi

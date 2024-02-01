from scrapy.cmdline import execute
import os
import sys
import platform

if platform.system() == "Windows":
    import asyncio
    asyncio.set_envent_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())

if __name__ == '__main__':
    # 获取当前脚本路径
    dirpath = os.path.dirname(os.path.abspath(__file__))
    #运行文件绝对路径
    print(os.path.abspath(__file__))
    #运行文件父路径
    print(dirpath)
    # 添加环境变量
    sys.path.append(dirpath)
    #切换工作目录
    os.chdir(dirpath)

    execute(['scrapy','crawl','zhetian'])
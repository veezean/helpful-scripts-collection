
## 1 功能介绍

根据给定的url列表，将每个url对应的html页面导出为一个png图片保存。

## 2 使用说明

**1. 安装chromedriver**

需要先下载与自己电脑上chrome浏览器版本相对应的chromedriver版本，[下载chromedriver](http://npm.taobao.org/mirrors/chromedriver/)

强调一下，**一定是要与自己chrome版本对应**，下载可以先到Chrome浏览器的`帮助--关于Chrome`页面查看下版本号，然后到上面的地址下载相对应的版本，然后`将chromedriver.exe文件放到Chrome浏览器的安装根目录`。

**2. 修改脚本中的chrome_driver值为真实的安装路径**

```python
# 修改为本机真实路径
chrome_driver='C:/Users/admin/AppData/Local/Google/Chrome/Application/chromedriver.exe'
```

**3. 将待导出的url信息添加到urls.txt文件中**

文件中每行填写一个url。

**4. 在CMD窗口执行命令**

```bat
python capture_page-png.py
```

*注意： 电脑上需要安装有python3*

**5. 导出的图片在result目录中**

## 3 其它说明
### 3.1 网速太差导致异步加载图片没显示问题

如果因为网速较慢等原因，导致异步加载的图片显示有问题，可以修改下脚本中的等待时间，保证图片加载完成后再生成图片。

```python
# 每次滚屏操作后，等待内容加载的时间（单位：秒）
scroll_wait_seconds=2
```
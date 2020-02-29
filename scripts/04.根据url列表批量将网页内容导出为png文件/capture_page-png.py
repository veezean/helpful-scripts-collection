from selenium import webdriver
import os
import time
from selenium.webdriver.chrome.options import Options

chrome_options = Options()
chrome_options.add_argument('--headless')
chrome_options.add_argument('--disable-gpu')
chrome_options.add_argument('--start-maximized')
urls = open("urls.txt") 

# 修改为本机真实路径
chrome_driver='C:/Users/admin/AppData/Local/Google/Chrome/Application/chromedriver.exe'

# 每次滚屏操作后，等待内容加载的时间（单位：秒）
scroll_wait_seconds=2


def scroll_page(browser, height):
    count = 0
    step = browser.execute_script("return document.body.scrollHeight;")
    print("step = " + str(step))
    print("height = " + str(height))
    while count < height:
        driver.execute_script('window.scrollTo(0,1000)')
        time.sleep(scroll_wait_seconds)
        count = count + 1000
        print("scrolling...")

for url in urls:
    driver = webdriver.Chrome(executable_path=chrome_driver, chrome_options=chrome_options)
    driver.set_page_load_timeout(30)
    print(url)
    
    try:
        driver.get(url)
    except Exception as ex:
        print("timeout")
        # TODO 可以判断下页面加载的数据量是否超过某个阈值，判断是否需要继续往后处理（解决有些界面加载完成的时间超长、页面加载一直不结束的情况，实际上需要的内容可能已经加载具备了）
    
    print("start scrolling...")
    
    try:
        # Get the actual page dimensions using javascript
        width = driver.execute_script("return Math.max(document.body.scrollWidth,document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth);")	 
        height = driver.execute_script("return Math.max(document.body.scrollHeight, document.body.offsetHeight,document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);")
        
        # 先让页面从上面滚动到下面，触发异步加载逻辑之后，再截取页面————解决异步加载的时候无法显示图片的问题
        scroll_page(driver, height)
    except Exception as exx:
        print("error...")
    
    
    print("scroll finished")
    
    #resize
    driver.set_window_size(width,height)
    time.sleep(5)
    driver.save_screenshot('result/' + str(hash(url)) + '.png')
    time.sleep(0.2)
    driver.quit()
#필수 패키지
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import pymysql

import Record_delete
import Record_save
import Product

table_deposit = "deposit"
table_savings = "savings"

def get_info (user_info) :

    driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용  창없는 모드
    #driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver') #창있는 모드 
    driver.get("https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608") #페이지이동
    cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
    productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
    rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
    description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

    bankname="KEB하나은행"
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,bankname,bankname,bankname) #기초화

    for i in range(0,cnt) :
        product.set_productname(productname[i].text)

        if "중단" in product.get_productname() :
            continue

        product.set_bankname(bankname)
        product.set_rate(rate[i].text)
        product.set_description(description[i].text)

        Record_save.save(table_deposit, bankname, product, user_info)



    # --------------------------하나은행 적금 --------------------------
    driver.execute_script("doTab('spb_2812')")
    cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
    productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
    rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
    description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화

    for i in range(0,cnt) :
        product.set_bankname(bankname)
        product.set_productname(productname[i].text)
        product.set_rate(rate[i].text)
        product.set_description(description[i].text)

        if "중단" in product.get_productname() :
            continue
       # driver.find_element_by_partial_link_text(productname[i].text).click()
        #driver.back()
        Record_save.save(table_deposit, bankname, product, user_info)



    
    driver.quit()

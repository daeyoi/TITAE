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
    driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용
    #driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver')
    driver.get("https://obank.kbstar.com/quics?page=C016613&cc=b061496:b061496#CP") #페이지이동
    cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수
    productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
    rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
    description = driver.find_elements_by_xpath("//span[@class='msg']")

    bankname="국민은행"
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,bankname,bankname,bankname) #기초
#예금
    for i in range(0,cnt-2) :
        product.set_bankname(bankname)
        product.set_productname(productname[i%10].text)
        product.set_rate(rate[i%10].text)
        product.set_description(description[i%10].text)
        Record_save.save(table_deposit, bankname, product, user_info)
    
        if i%10 ==9 :
            if(cnt >=10) :
                driver.execute_script("goPage(arguments[0])",page) #페이지 이동
                #element = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "area1")))
                driver.implicitly_wait(3)     
                cnt = cnt -10
                print("---------------")
                page = page + 1
                time.sleep(1)
                productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
                rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
                description = driver.find_elements_by_xpath("//span[@class='msg']")

#적금
    driver.execute_script("fnMenuClick('00027')")
    time.sleep(1)
    cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수

    #필요항목 추출
    productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
    rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
    description = driver.find_elements_by_xpath("//span[@class='msg']")
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    page = int(2)

    for i in range(0,cnt) :

        product.set_productname(productname[i%10].text)
        product.set_rate(rate[i%10].text)
        product.set_description(description[i%10].text)
        Record_save.save(table_deposit, bankname, product, user_info)
        
        if i%10 == 9 :
            if(cnt >=10) :
                driver.execute_script("goPage(arguments[0])",page) 
                cnt = cnt -10
                page = page + 1
                time.sleep(1)
                productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
                rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
                description = driver.find_elements_by_xpath("//span[@class='msg']")

    driver.quit()

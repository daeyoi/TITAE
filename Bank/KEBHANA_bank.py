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


    options = Options()
    #밑에 2줄 주석 처리시 창보임
    #options.add_argument('headless')
    #options.add_argument('--disable-gpu')
    
    #driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용  창없는 모드
    driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver',options=options) #창있는 모드 
    driver.get("https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608") #페이지이동
    cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
    productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
    rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
    description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

    bankname="KEB하나은행"
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,None,None,None,None,None,None) #기초

    for i in range(14,cnt) :

        if "중단" in productname[i].text :
            continue
        
        product.set_bankname(bankname)
        product.set_productname(productname[i].text)
        print(product.get_productname())
        product.set_description(description[i].text)
        product.set_rate(rate[i].text)

        driver.find_element_by_partial_link_text(productname[i].text).click()# 상품페이지로 이동
        time.sleep(2)

        if product.get_productname() == '정기예금' :
            target = "제한없음"
        else :
            target = driver.find_elements_by_xpath("//dl[@class='prodcutInfo']/dd")[1].text
       

        if "제한없음" in target :
            target="제한없음"
        elif "서민" in target :
            target="서민전용"
        else :
            target="일부제한"
        product.set_target(target)
        product.set_calmethod("복리") # 임시
        
        

        Record_save.save(table_deposit, bankname, product, user_info)
        driver.back()
        time.sleep(1)
        productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
        rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
        description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")


# --------------------------하나은행 적금 --------------------------
    driver.execute_script("doTab('spb_2812')")
    time.sleep(1)
    cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
    productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
    rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
    description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

    Record_delete.delete(table_savings,bankname,user_info) #해당 은행 데이터 초기화

    for i in range(0,cnt) :
        print(i)

        if "중단" in productname[i].text :
            continue
        product.set_bankname(bankname)
        product.set_productname(productname[i].text)
        product.set_description(description[i].text)
        product.set_rate(rate[i].text)
        product.set_calmethod("단리") # 임시

        driver.find_element_by_partial_link_text(productname[i].text).click()# 상품페이지로 이동
        print(product.get_productname())
        target = driver.find_elements_by_xpath("//dl[@class='prodcutInfo']/dd")[1].text
        if "제한없음" in target :
            target="제한없음"
        elif "서민" in target :
            target="서민전용"
        else :
            target="일부제한"

        rsm_tmp = driver.find_elements_by_xpath("//dl[@class='prodcutInfo']/dt")

        j_cnt = 0
        for j in rsm_tmp :
            if "적립방법" in j.text :
                rsm = driver.find_elements_by_xpath("//dl[@class='prodcutInfo']/dd")[j_cnt].text
            j_cnt = j_cnt + 1
            
        print(rsm)
        if"자유" in rsm :
            product.set_reservingmethod("자유")
            Record_save.save(table_savings, bankname, product, user_info)
        elif "매월" in rsm:
            product.set_reservingmethod("자유")
            Record_save.save(table_savings, bankname, product, user_info)
            
        if "정액" in rsm :
            product.set_reservingmethod("정액")
            Record_save.save(table_savings, bankname, product, user_info)

        driver.back()
        productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
        rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
        description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")
        time.sleep(2)



    
    driver.quit()

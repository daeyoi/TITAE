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
    options = Options()
    #밑에 세줄 주석처리시 크롬창으로 확인 가능 
    #options.add_argument('headless')
   
    #options.add_argument('--disable-gpu')
    #driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용
    driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver',options=options)
    driver.get("https://obank.kbstar.com/quics?page=C016613&cc=b061496:b061496#CP") #페이지이동
    cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수

    bankname="국민은행"
    productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
    description = driver.find_elements_by_xpath("//span[@class='msg']")
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,None,None,None,None,None,None) #기초
#예금
    for i in range(0,cnt) :
        productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
        description = driver.find_elements_by_xpath("//span[@class='msg']")
        product.set_description(description[i%10].text)
        product.set_productname(productname[i%10].text)
        wait = WebDriverWait(driver, 10)
        wait.until(EC.element_to_be_clickable((By.CLASS_NAME, 'title')))
        
        driver.find_element_by_partial_link_text(productname[i].text).click()# 상품페이지로 이동
        driver.implicitly_wait(10)
        

        target = driver.find_elements_by_xpath("//div[@class='infoCont']")[2].text
        if "제한없음" in target :
            target="제한없음"
        elif "서민" in target :
            target="서민전용"
        else :
            target="일부제한"

        try :        
            product.set_rate(driver.find_element_by_xpath("//span[@class='info-data2']/strong/span").text)
        except :
            product.set_rate(driver.find_element_by_xpath("//strong[@class='point_blue point-st01']").text[1:-1])
            
       
            
        product.set_bankname(bankname)
        product.set_target(target)
        product.set_calmethod("복리") #임시
        Record_save.save(table_deposit, bankname, product, user_info)
        
        print(product.get_rate())
        print(product.get_description())
        print(product.get_target())
        
        driver.back()
        time.sleep(3)

        if i%10 ==9 :
            if(cnt >=10) :
                driver.execute_script("goPage(arguments[0])",page) #페이지 이동
               
                driver.implicitly_wait(3)     
                cnt = cnt -10
                print("---------------")
                page = page + 1
                time.sleep(1)
                

#적금
    driver.execute_script("fnMenuClick('00027')")
    time.sleep(1)
    cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수

    #필요항목 추출

    Record_delete.delete(table_savings,bankname,user_info) #해당 은행 데이터 초기화
    page = int(2)

    for i in range(0,cnt) :
        productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
        description = driver.find_elements_by_xpath("//span[@class='msg']")
        product.set_description(description[i%10].text)
        product.set_productname(productname[i%10].text)
        
        wait = WebDriverWait(driver, 10)
        wait.until(EC.element_to_be_clickable((By.CLASS_NAME, 'title')))
        
        driver.find_element_by_partial_link_text(productname[i%10].text).click()# 상품페이지로 이동
        driver.implicitly_wait(10)

        try :
            product.set_rate(driver.find_element_by_xpath("//span[@class='info-data2']/strong/span").text)
        except :
            product.set_rate(driver.find_element_by_xpath("//strong[@class='point_blue point-st01']").text[1:-1])
        
        target = driver.find_elements_by_xpath("//div[@class='infoCont']")[2].text
        if "제한없음" in target :
            target="제한없음"
        elif "서민" in target :
            target="서민전용"
        else :
            target="일부제한"

    

                    
        product.set_bankname(bankname)
        product.set_target(target)
        product.set_calmethod("단리") #임시


      
            
        rsm = driver.find_elements_by_xpath("//strong[@class='point_blue point-st01']")
        
        for j in rsm :
            if"자유" in j.text :
                product.set_reservingmethod("자유")
                Record_save.save(table_savings, bankname, product, user_info)
                if "정액" in j.text :
                    product.set_reservingmethod("정액")
                    Record_save.save(table_savings, bankname, product, user_info)
                break


        print(i)
        print(product.get_productname())
        print(product.get_rate())
        print(product.get_description())
        print(product.get_target())
        print(product.get_reservingmethod())
        
        driver.execute_script("inqDeposit()")
        time.sleep(3)
        
        #페이지 이동
        if i%10 == 9 :
            if(cnt >=10) :
                driver.execute_script("goPage(arguments[0])",page) 
                cnt = cnt -10
                page = page + 1
                time.sleep(1)                
              

    driver.quit()

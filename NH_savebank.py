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
    #밑에 2줄 주석 처리시 창보임
    #options.add_argument('headless')
    #options.add_argument('--disable-gpu')
    
    #driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용  창없는 모드
    driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver',options=options) #창있는 모드 
    driver.get("https://www.nhsavingsbank.co.kr/dpisPrd/view.do?prdCode=24501") #페이지이동
    
#-----------------------NH저축  예금 ---------------------
    
    bankname="NH저축은행"
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,None,None,None,None,None,None) #초기화
    product.set_bankname(bankname)

    product.set_productname("비대면 정기예금")
    product.set_rate("1.9")
    product.set_description("여유자금을 일정기간 예치하고 만기시 약정금리로 고수익을 보장하는 예금")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    Record_save.save(table_deposit, bankname, product, user_info)

    product.set_productname("비대면 정기예금")
    product.set_rate("1.92")
    product.set_description("여유자금을 일정기간 예치하고 만기시 약정금리로 고수익을 보장하는 예금")
    product.set_target("제한없음")
    product.set_calmethod("복리")
    Record_save.save(table_deposit, bankname, product, user_info)
    
    driver.get("https://www.nhsavingsbank.co.kr/dpisPrd/view.do?prdCode=24011") #페이지이동
    product.set_productname("중도해지 Good 정기예금")
    product.set_rate("1.8")
    product.set_description("중도해지시에도 기간별 약정금리를 보장하는 예금")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    Record_save.save(table_deposit, bankname, product, user_info)

    product.set_productname("중도해지 Good 정기예금")
    product.set_rate("1.83")
    product.set_description("중도해지시에도 기간별 약정금리를 보장하는 예금")
    product.set_target("제한없음")
    product.set_calmethod("복리")
    Record_save.save(table_deposit, bankname, product, user_info)


# -------------------------- NH저축  적금 --------------------------
    

    driver.get("https://www.nhsavingsbank.co.kr/dpisPrd/view.do?prdCode=31501") #페이지이동
    product.set_productname("비대면 정기적금(정액식)")
    product.set_rate("2.6")
    product.set_description("약정기간 매월 일정액을 불입하여 목돈마련 저축")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    product.set_reservingmethod("정액")
    Record_save.save(table_savings, bankname, product, user_info)

    driver.get("https://www.nhsavingsbank.co.kr/dpisPrd/view.do?prdCode=31511") #페이지이동
    product.set_productname("비대면 정기적금(자유식)")
    product.set_rate("1.7")
    product.set_description("약정기간 금액을 자유롭게 불입할 수 있는 목돈마련 저축.")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    product.set_reservingmethod("자유")
    Record_save.save(table_savings, bankname, product, user_info)


    
    driver.quit()

import scrapy
import time
import random
import re


class ZhetianSpider(scrapy.Spider):
    name = "qingyunian"
    allowed_domains = ["m2.xxyanqing.net"]
    start_urls = ["https://m2.xxyanqing.net/book/95219149/58639046.html"]
    base_page = ''
    end_page = '/book/95219149/'
    base_url = 'https://m2.xxyanqing.net'
    book_name = '庆余年.txt'
    current_title = '';

    def parse(self, response):
        if response.status == 503:
            print(f'save {response._url} 失败，即将开始重试')
            time.sleep(random.uniform(1,3))
            r = response.request.copy()
            r.dont_filter = True
            yield r
            return
        
        if (not response.text or self.saveParagraph(response=response) is False):
            print(f'save {response._url} 失败，即将开始重试')
            time.sleep(random.uniform(1,3))
            r = response.request.copy()
            r.dont_filter = True
            yield r
            return


        next_page = response.xpath('//p[@class="Readpage"]//a[text()="下一章"]/@href').extract_first()
        if next_page is None:
            next_page = response.xpath('//p[@class="Readpage"]//a[text()="下一页"]/@href').extract_first()
        
        if next_page == self.end_page:
            print(f'find completed {next_page}')
        else:
            if next_page.startswith(self.base_page) is False:
                next_page = self.base_page + next_page
            print(f'find next chapter {self.base_url}{next_page}')
            #time.sleep(random.uniform(1,3))
            yield scrapy.Request(self.base_url + next_page, callback=self.parse)

    def saveParagraph(self, response):
        title = response.css(".title::text").get()
        if title is None:
            return False
        
        is_next_page = response.xpath('//p[@class="Readpage"]//a[text()="下一章"]/@href').extract_first().find('_') == -1

        title = title.strip().replace('《庆余年》电视剧_','')
        if is_next_page is True:
            print(f'find chapter {title}')
        else:
            print(f'find chapter {title} 还有下一页')
        
        with open(self.book_name, 'a', encoding='utf-8') as f:
            if self.current_title != title:
                f.write(title)
                f.write('\r\n\r\n')
                self.current_title = title

            contents = response.css("#chaptercontent::text")

            for content in contents:
                paragraph = content.get().replace('\xa0','').replace('\r','').strip()
                if paragraph == '':
                    continue
                if paragraph == title or re.fullmatch(r'^第\(.*\)页$', paragraph) or paragraph == '(本章未完,请翻页)' or paragraph == '记住手机版网址：m2.xxyanqing.net':
                    continue
                # print(paragraph)
                f.write('\t' + paragraph)
                f.write('\r\n')
            
            if is_next_page is True:
                f.write('\r\n\r\n')
        return True


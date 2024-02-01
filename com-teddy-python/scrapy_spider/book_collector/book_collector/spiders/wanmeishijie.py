import scrapy
import time
import random


class ZhetianSpider(scrapy.Spider):
    name = "wanmeishijie"
    allowed_domains = ["yadaiercn.com"]
    start_urls = ["http://www.yadaiercn.com/ldks/16478/4501007.html"]
    base_page = '/ldks/16478/'
    end_page = '4532422.html'
    base_url = 'http://www.yadaiercn.com'
    book_name = '完美世界.txt'

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


        next_page = response.xpath('//div[@class="section-opt m-bottom-opt"]//a[text()="下一章"]/@href').extract_first()
        if next_page is None:
            next_page = response.xpath('//div[@class="section-opt m-bottom-opt"]//a[text()="下一页"]/@href').extract_first()
        
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
        
        is_next_page = response.xpath('//div[@class="section-opt m-bottom-opt"]//a[text()="下一页"]/@href').extract_first() is None

        title = title.strip()
        if is_next_page is True:
            print(f'find chapter {title}')
        else:
            print(f'find chapter {title} 还有下一页')
        
        with open(self.book_name, 'a', encoding='utf-8') as f:
            if is_next_page is False:
                f.write(title)
                f.write('\r\n\r\n')

            contents = response.css("#content::text")

            for content in contents:
                paragraph = content.get().replace('\xa0','').replace('\r','').strip()
                if paragraph == '':
                    continue
                # print(paragraph)
                f.write('\t' + paragraph)
                f.write('\r\n')
            
            if is_next_page is True:
                f.write('\r\n\r\n')
        return True


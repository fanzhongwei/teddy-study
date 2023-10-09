import scrapy
import time
import random


class ZhetianSpider(scrapy.Spider):
    name = "wanmeishijie"
    allowed_domains = ["ibiquges.com"]
    start_urls = ["https://www.ibiquges.com/8/8345/3734159.html"]
    end_next_page = '/8/8345/'
    base_url = 'http://www.ibiquges.com'
    book_name = '完美世界.txt'

    def parse(self, response):
        
        if self.saveParagraph(response=response) is False:
            print(f'save {response._url} 失败，即将开始重试')
            time.sleep(random.uniform(1,3))
            r = response.request.copy()
            r.dont_filter = True
            yield r
            return


        next_page = response.xpath('//div[@class="bottem1"]//a[text()="下一章"]/@href').extract_first()
        if len(next_page) <= len(self.end_next_page):
            print(f'find completed {next_page}')
        else:
            print(f'find next chapter {self.base_url}{next_page}')
            time.sleep(random.uniform(1,3))
            yield scrapy.Request(self.base_url + next_page, callback=self.parse)

    def saveParagraph(self, response):
        title = response.css(".bookname>h1::text").get()
        if title is None:
            return False
        
        title = title.strip()
        print(f'find chapter {title}')
        with open(self.book_name, 'a', encoding='utf-8') as f:
            f.write(title)
            f.write('\r\n\r\n')

            contents = response.css("#content::text")

            for content in contents:
                paragraph = content.get().replace('\xa0','').replace('\r','')
                if paragraph == '':
                    continue
                # print(paragraph)
                f.write('\t' + paragraph)
                f.write('\r\n')
            
            f.write('\r\n\r\n')
        return True


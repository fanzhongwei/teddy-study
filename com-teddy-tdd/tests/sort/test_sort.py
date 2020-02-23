#!/usr/bin/python

import unittest

from study.sort import sort


class TestSort(unittest.TestCase):

    def test_int_sort(self):
        self.assertSequenceEqual([1, 2, 3, 4, 5, 6, 7, 8, 9], sort.sort(2, 3, 4, 6, 8, 9, 7, 5, 1))

    def test_string_sort(self):
        self.assertSequenceEqual(['1', '2', '3', '4', '5', '6', '7', '8', '9'],
                                 sort.sort('2', '3', '4', '6', '8', '9', '7', '5', '1'))

    def test_float_sort(self):
        self.assertSequenceEqual([1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9],
                                 sort.sort(2.2, 3.3, 4.4, 6.6, 8.8, 9.9, 7.7, 5.5, 1.1))

    def test_int_quick_sort(self):
        self.assertSequenceEqual([1, 2, 3, 4, 5, 6, 7, 8, 9], sort.quick_sort(9, 2, 3, 1, 6, 8, 7, 5, 4))

    def test_string_quick_sort(self):
        self.assertSequenceEqual(['1', '2', '3', '4', '5', '6', '7', '8', '9'],
                                 sort.quick_sort('2', '3', '4', '6', '8', '9', '7', '5', '1'))

    def test_float_quick_sort(self):
        self.assertSequenceEqual([1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9],
                                 sort.quick_sort(2.2, 3.3, 4.4, 6.6, 8.8, 9.9, 7.7, 5.5, 1.1))


if __name__ == '__main__':
    unittest.main()

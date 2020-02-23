#!/usr/bin/python
import unittest


from study.operator.Integer import Integer


class TestOperator(unittest.TestCase):

    def test_integer_1_equals_integer_1(self):
        self.assertEqual(Integer(1), Integer(1))

    def test_integer_1_not_equals_integer_2(self):
        self.assertNotEqual(Integer(1), Integer(2))

    def test_integer_1_add_integer_2_equals_integer_3(self):
        self.assertEqual(Integer(3), Integer(1) + Integer(2))

    def test_integer_1_add_2_equals_integer_3(self):
        self.assertEqual(Integer(3), Integer(1) + 2)

    def test_integer_3_reduce_integer_1_equals_integer_2(self):
        self.assertEqual(Integer(2), Integer(3) - Integer(1))

    def test_integer_2_multiply_integer_3_equals_integer_6(self):
        self.assertEqual(Integer(6), Integer(2) * Integer(3))

    def test_integer_6__integer_2_equals_3(self):
        self.assertEqual(Integer(3), Integer(6) / Integer(2))


if __name__ == '__main__':
    unittest.main()

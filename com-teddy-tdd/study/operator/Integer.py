#!/usr/bin/python3
class Integer:

    def __init__(self, num: int) -> None:
        self.num = num

    def __eq__(self, other):
        if other is None:
            return False
        if isinstance(other, Integer):
            return other.num == self.num
        return False

    def __hash__(self):
        return hash(self.num)

    def __add__(self, other):
        if isinstance(other, Integer):
            return Integer(self.num + other.num)
        if isinstance(other, int):
            return Integer(self.num + other)
        return self

    def __sub__(self, other):
        if isinstance(other, Integer):
            return Integer(self.num - other.num)
        return self

    def __mul__(self, other):
        if isinstance(other, Integer):
            return Integer(self.num * other.num)
        return self

    def __truediv__(self, other):
        if isinstance(other, Integer):
            return Integer(self.num / other.num)
        return self

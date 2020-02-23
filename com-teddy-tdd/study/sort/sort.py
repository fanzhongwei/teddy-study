#!/usr/bin/python


def sort(*param) -> list:
    arr = list(param)
    for i in range(len(arr)):
        for j in range(i, len(arr)):
            if arr[i] > arr[j]:
                temp = arr[j]
                arr[j] = arr[i]
                arr[i] = temp
    return arr


def quick_sort(*param) -> list:
    return __quick_sort(list(param))


def __quick_sort(params: list) -> list:
    if len(params) <= 1:
        return params
    left = []
    right = []
    base = params.pop()
    for a in params:
        if a <= base:
            left.append(a)
        else:
            right.append(a)
    result = __quick_sort(left)
    result.append(base)
    result.extend(__quick_sort(right))
    return result

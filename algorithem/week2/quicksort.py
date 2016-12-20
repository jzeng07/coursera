"""
    Coursera algorithems: Design and Analysis, Part 1, week 2,
    Programming Question #2
"""

import random

def swap(a, i, j):
    tmp = a[i]
    a[i] = a[j]
    a[j] = tmp

def quicksort(a, lo, hi, ptype):
    count = 0
    if lo < hi:
        if ptype == "first":
            p, recur_count = partition_first(a, lo, hi)
            l_count = quicksort(a, lo, p-1, "first")
            r_count = quicksort(a, p+1, hi, "first")
            count = recur_count + l_count + r_count
        elif ptype == "last":
            swap(a, lo, hi)
            p, recur_count = partition_first(a, lo, hi)
            l_count = quicksort(a, lo, p-1, "last")
            r_count = quicksort(a, p+1, hi, "last")
            count = recur_count + l_count + r_count
        elif ptype == "random":
            p, recur_count = partition_random(a, lo, hi)
            l_count = quicksort(a, lo, p-1, "random")
            r_count = quicksort(a, p+1, hi, "random")
            count = recur_count + l_count + r_count
        elif ptype == "median":
            mi = (hi + lo)/2
            pivots = [(a[lo], lo), (a[mi], mi), (a[hi], hi)]
            pivots.sort()
            p_index = pivots[1][1]
            swap(a, lo, p_index)

            p, recur_count = partition_first(a, lo, hi)
            l_count = quicksort(a, lo, p-1, "median")
            r_count = quicksort(a, p+1, hi, "median")
            count = recur_count + l_count + r_count
    return count


def partition_first(a, lo, hi):
    p = a[lo]
    i = lo + 1
    count = 0
    for j in xrange(lo+1, hi+1):
        count += 1
        if a[j] < p:
            swap(a, i, j)
            i += 1
    swap(a, lo, i-1)
    return i - 1, (hi - lo)
    #return i - 1, count


def partition_last(a, lo, hi):
    p = a[hi]
    i = lo
    count = 0
    for j in xrange(lo, hi):
        count += 1
        if a[j] < p:
            swap(a, i, j)
            i += 1
    swap(a, hi, i)
    return i, (hi - lo)
    #return i, count

def partition_median(a, lo, hi):
    mi = (hi - lo)/2 + lo
    pivots = [(a[lo], lo), (a[mi], mi), (a[hi], hi)]
    pivots.sort()
    p = pivots[1][0]
    count = 0

    i = lo
    j = hi
    while i < j:
        while a[i] < p:
          count += 1
          i += 1
        while a[j] > p:
          count += 1
          j -= 1

        swap(a, i, j)
    return j, (hi - lo)
    #return p_index, (hi - lo)
    #return p_index, count
 
def partition_random(a, lo, hi):
    p_index = random.randint(lo, hi)
    p = a[p_index]

    i = lo
    j = hi
    while i < j:
        while a[i] < p:
          i += 1
        while a[j] > p:
          j -= 1

        if a[i] == a[j] and i < j:
            if a[i] <= a[i+1]:
                i += 1
            else:
                j -= 1
            continue
        swap(a, i, j)
        if a[i] == p:
            p_index = i
        if a[j] == p:
            p_index = j
    return p_index, (hi - lo)
        
data = [int(line) for line in open("QuickSort.txt", "r")]
data_first = [x for x in data]
data_last = [x for x in data]
data_median = [x for x in data]
data.sort()

count_first = quicksort(data_first, 0, len(data_first)-1, "first")
count_last = quicksort(data_last, 0, len(data_last)-1, "last")
count_median = quicksort(data_median, 0, len(data_median)-1, "median")

assert(data == data_first)
assert(data == data_last)
assert(data == data_median)

print "Comparison count with first: ", count_first
print "Comparison count with last: ", count_last
print "Comparison count with median: ", count_median


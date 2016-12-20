
def read_data(f, ratio=True):
    lines = [l for l in open(f, "r")]
    data = []
    for l in lines:
        if not " " in l:
            continue
        w, l = l.strip().split()
        r = float(w) / float(l)
        if not ratio:
            r = int(w) - int(l)
        data.append((r, int(w), int(l)))

    return sorted(data, reverse=True)


def schedule(f, ratio=True):
    jobs = read_data(f, ratio)
    completion = 0
    inc = 0
    for job in jobs:
        completion += job[1] * (job[2] + inc)
        inc += job[2]

    return completion


print schedule("jobs.txt", ratio=False)
print schedule("jobs.txt")



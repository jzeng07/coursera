fun is_older(date1: (int*int*int), date2: (int*int*int)) =
    if #1 date1 < #1 date2
    then true
    else
        if #1 date1 > #1 date2
        then false
        else
            if #2 date1 < #2 date2
            then true
            else
                if #2 date1 > #2 date2
                then false
                else #3 date1 < #3 date2


fun number_in_month (dates: (int*int*int) list, month: int) =
  if null dates
  then 0
  else
    if #2 (hd dates) = month
    then 1 + number_in_month(tl dates, month)
    else 0 + number_in_month(tl dates, month)


fun number_in_months (dates: (int*int*int) list, months: int list) =
  if null months
  then 0
  else
    number_in_month(dates, hd months) + number_in_months(dates, tl months)


fun dates_in_month(dates: (int*int*int) list, month: int) =
  if null dates
  then []
  else
    if #2 (hd dates) = month
    then hd dates :: dates_in_month(tl dates, month)
    else dates_in_month(tl dates, month)


fun dates_in_months (dates: (int*int*int) list, months: int list) =
  if null months
  then []
  else
    dates_in_month(dates, hd months) @ dates_in_months(dates, tl months)


fun get_nth(s: string list, n: int) =
  if n = 1
  then hd s
  else get_nth(tl s, n-1)


fun date_to_string (date: int*int*int) =
  get_nth(["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"], #2 date) ^
  " " ^ Int.toString(#3 date) ^ ", " ^ Int.toString(#1 date)


fun number_before_reaching_sum(sum: int, numbers: int list) =
  if sum <= hd numbers
  then 0
  else 1 + number_before_reaching_sum(sum - hd numbers, tl numbers)


fun what_month (day: int) =
  number_before_reaching_sum(day, [31,28,31,30,31,30,31,31,30,31,30,31]) + 1


fun month_range(day1: int, day2: int) =
  if day1 > day2
  then []
  else what_month(day1)::month_range(day1+1, day2)


fun oldest(dates: (int*int*int) list) =
    if null dates
    then NONE
    else
        let
            fun get_oldest(dates: (int*int*int) list) =
                if null (tl dates)
                then hd dates
                else let val tl_ans = get_oldest(tl dates)
                in
                    if is_older(tl_ans, hd dates)
                    then tl_ans
                    else hd dates
                end
        in
            SOME (get_oldest(dates))
        end


fun number_in_months_challenge(dates: (int*int*int) list, months: int list) =
    let
        fun in_month_list (month: int, months: int list) =
            if null months
            then false
            else
                if month = hd months
                then true
                else in_month_list(month, tl months)

        fun remove_duplicate(months: int list) = 
          if null months
          then []
          else
            if in_month_list(hd months, tl months) = true
            then remove_duplicate(tl months)
            else hd months :: remove_duplicate(tl months)
    in
        number_in_months(dates, remove_duplicate(months))
    end


fun dates_in_months_challenge(dates: (int*int*int) list, months: int list) =
    let
        fun in_month_list (month: int, months: int list) =
            if null months
            then false
            else
                if month = hd months
                then true
                else in_month_list(month, tl months)

        fun remove_duplicate(months: int list) = 
          if null months
          then []
          else
            if in_month_list(hd months, tl months) = true
            then remove_duplicate(tl months)
            else hd months :: remove_duplicate(tl months)
    in
        dates_in_months(dates, remove_duplicate(months))
    end


fun reasonable_date(date: int*int*int) =
    if #1 date < 1
    then false
    else
        if #2 date < 1 orelse #2 date > 12
        then false
        else
            let 
                fun is_leap_year(year: int) =
                    if (year mod 4 = 0 andalso year mod 100 <> 0) orelse year mod 400 = 0
                    then true
                    else false

                fun in_month_list (month: int, months: int list) =
                    if null months
                    then false
                    else
                        if month = hd months
                        then true
                        else in_month_list(month, tl months)

                fun date_max (month: int) =
                    if in_month_list(#2 date, [1,3,5,7,8,10,12])
                    then 31
                    else
                        if in_month_list(#2 date, [4,6,9,11])
                        then 30
                        else
                            if is_leap_year(#1 date)
                            then 29
                            else 28
            in
                if #3 date < 1 orelse #3 date > date_max(#2 date)
                then false
                else true
            end


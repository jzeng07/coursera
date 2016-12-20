(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* 1-a *)
fun all_except_option (s, xs) =
  let fun split (s, xs) =
    case xs of
      [] => xs
      | x::xs' => if s = x then xs' else x::split(s, xs')
  val y = split (s, xs)
  in
    if y = xs then NONE else SOME y
  end


(* 1-b *)
fun get_substitutions1 (xss, s) =
  case xss of
    [] => []
    | xs::xss' => let val y = all_except_option(s, xs)
      in
        case y of
        NONE => get_substitutions1 (xss', s)
        | SOME y => y @ get_substitutions1 (xss', s)
      end

(* 1-c *)
fun get_substitutions2 (xss, s) =
  let fun aux (xss, s, acc) = 
    case xss of
      [] => acc
      | xs::xss' => let val y = all_except_option(s, xs)
        in
          case y of
            NONE => aux (xss', s, acc)
            | SOME y => aux (xss', s, y @ acc)
        end
  in
    aux (xss, s, [])
  end

(* 1-d *)
fun similar_names (xss, fullname) =
  case fullname of
    {first=fname,middle=mname,last=lname} => let val ys = get_substitutions1(xss, fname)
      in
        let fun aux (ys,mname,lname,acc) =
          case ys of
            [] => acc
            | y::ys' => aux(ys',mname,lname,{first=y,middle=mname,last=lname}::acc)
        in
          aux (ys,mname,lname,[fullname])
        end
      end

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

(* 2-a *)
fun card_color (card) =
    case card of
      (Spades, _) => Black
      | (Clubs, _) => Black
      | (_, _) => Red

(* 2-b *)
fun card_value (card) =
    case card of
      (_, Num i) => i
      | (_, Ace) => 11
      | (_, _) => 10

(* 2-c *)
fun remove_card (cs, c, e) =
    let 
      fun split (cs, c) =
        case cs of
        [] => cs
        | c'::cs' => if c' = c then cs' else c' :: split(cs', c)
      val y = split (cs, c)
    in
      if y = cs then raise e else y
    end

(* 2-d *)
fun all_same_color cs =
    case cs of
    [] => true
    | _::[] => true
    | head::(neck::tail) => card_color(head) = card_color(neck) andalso all_same_color (neck::tail)

(* 2-e *)
fun sum_cards cs =    
    let fun aux (cs, acc) =
      case cs of
      [] => acc
      | c::cs' => aux (cs', card_value(c) + acc)
    in
      aux (cs, 0)
    end

(* 2-f *)
fun score (cs, goal) =
    let val sum = sum_cards cs
    in
        if sum < goal then goal - sum
        else if sum > goal then 3 * (sum -goal) else 0
    end

(* 2-g *)
fun officiate (held, move, goal) = 0

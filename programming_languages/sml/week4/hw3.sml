(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)

(* 1 *)
fun only_capitals xs =
  let val f = fn s => (Char.isUpper o String.sub) (s, 0)
  in List.filter f xs
  end

(* 2 *)
fun longest_string1 xs =
  let val f = fn (s1, s2) => if String.size(s1) > String.size(s2) then s1 else s2
  in List.foldl f "" xs end

(* 3 *)
fun longest_string2 xs =
  let val f = fn (s1, s2) => if String.size(s1) >= String.size(s2) then s1 else s2
  in List.foldl f "" xs end

(* 4 *)
fun longest_string_helper f xs =
  let val fhelp = fn (s1, s2) => if f(String.size(s1), String.size(s2)) = true then s1 else s2
  in List.foldl fhelp "" xs end

fun longest_string3 xs =
  longest_string_helper (fn (a, b) => if a > b then true else false) xs

fun longest_string4 xs =
  longest_string_helper (fn (a, b) => if a >= b then true else false) xs


(* 5 *)
fun longest_capitalized xs =
  let val f = longest_string1 o only_capitals
  in f xs end

(* 6 *)
fun rev_string s =
  (String.implode o rev o String.explode) s

(* 7 *)
fun first_answer f xs =
  case xs of
    [] => raise NoAnswer
    | x::xs' => case f x of
                NONE => first_answer f xs'
                | SOME x => x
                
(* 8 *)
fun all_answers f xs =
  let fun aux xs =
    case xs of
      [] => SOME []
      |x::xs' => case f x of
          NONE => NONE 
        | SOME x => let val rem = aux xs' in
            case rem of NONE => NONE | SOME y => SOME (x @ y) end
  in
    aux xs
  end

 
(* 9 *)
(* 9-a *)
fun f1 () = 1
fun count_wildcards p =
  let fun f1 () = 1; fun f2 s = 0
  in g f1 f2 p end

(* 9-b *)
fun count_wild_and_variable_lengths p =
  let fun f1 () = 1; fun f2 s = String.size s
  in g f1 f2 p end

(* 9-c *)
fun count_some_var (s, p) =
  let fun f1 () = 0; fun f2 x = if x = s then 1 else 0
  in g f1 f2 p end

  
(* 10 *)
fun check_pat p =
  let fun get_string p =
	case p of
	    Variable x        => [x]
      | ConstructorP(s, p') => [] @ get_string p'
	  | TupleP ps         => List.foldl (fn (p',i) => i @ (get_string p')) [] ps
	  | _                 => []

    fun no_repeat xs =
      case xs of
        [] => true
        | x::[] => true
        | x::xs' => (List.exists (fn y => x <> y) xs') andalso no_repeat(xs')
  in (no_repeat o get_string) p end

(* 11 *)
fun match vp =
  case vp of
      (_, Wildcard) => SOME []
    | (v, Variable s) => SOME [(s, v)]
    | (Unit, UnitP) => SOME []
    | (Const x, ConstP y) => if x = y then SOME [] else NONE
    | (Constructor (s1, v), ConstructorP (s2, p)) => if s1 = s2 then match(v, p) else NONE
    | (Tuple vs, TupleP ps) =>
        let val vp_pairs = ListPair.zip(vs, ps) in
          all_answers match vp_pairs
        end
    | (_, _) => NONE

(* 12 *)
fun first_match v ps =
  case ps of
    [] => NONE
    | p::ps' => let val m = match (v, p) in
      if m = NONE then first_match v ps' else m end

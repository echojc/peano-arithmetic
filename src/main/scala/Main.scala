object Main extends App {

  trait Nat {
    type Prev <: Nat
    type Plus[A <: Nat] <: Nat
    type Times[A <: Nat] <: Nat
    type Divide[A <: Nat] <: Nat
    type suniM[A <: Nat] <: Nat
    type rewoP[A <: Nat] <: Nat
    type Mod[A <: Nat] <: Nat
    type dcG[A <: Nat] <: Nat

    type DivideInternal[A <: Nat] <: Nat
  }

  trait _0 extends Nat {
    type Prev = _0
    type Plus[A <: Nat] = A
    type Times[A <: Nat] = _0
    type Divide[A <: Nat] = _0
    type suniM[A <: Nat] = A
    type rewoP[A <: Nat] = _1
    type Mod[A <: Nat] = _0
    type dcG[A <: Nat] = A

    type DivideInternal[A <: Nat] = _0
  }

  trait Succ[N <: Nat] extends Nat {
    type Prev = N
    type Plus[A <: Nat] = N#Plus[Succ[A]]
    type Times[A <: Nat] = A#Plus[N#Times[A]]
    type Divide[A <: Nat] = Succ[This]#DivideInternal[A]#Prev
    type suniM[A <: Nat] = N#suniM[A#Prev]
    type rewoP[A <: Nat] = A#Times[N#rewoP[A]]
    type Mod[A <: Nat] = This#Divide[A]#Times[A]#suniM[This] // not inductive
    type dcG[A <: Nat] = A#Mod[This]#dcG[This]

    type This = Succ[N]
    type DivideInternal[A <: Nat] = Succ[A#suniM[This]#DivideInternal[A]]
  }

  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]
  type _6 = Succ[_5]
  type _7 = Succ[_6]
  type _8 = Succ[_7]
  type _9 = Succ[_8]

  type Plus[A <: Nat, B <: Nat] = A#Plus[B]
  type Minus[A <: Nat, B <: Nat] = B#suniM[A]
  type Times[A <: Nat, B <: Nat] = A#Times[B]
  type Divide[A <: Nat, B <: Nat] = A#Divide[B]
  type Power[A <: Nat, B <: Nat] = B#rewoP[A]
  type Mod[A <: Nat, B <: Nat] = A#Mod[B]
  type Gcd[A <: Nat, B <: Nat] = B#dcG[A]

  implicitly[_5 =:= Plus[_2, _3]]
  implicitly[_6 =:= Times[_3, _2]]
  implicitly[_8 =:= Power[_2, _3]]
  implicitly[_3 =:= Minus[_5, _2]]
  implicitly[_2 =:= Divide[_4, _2]]
  implicitly[_1 =:= Divide[_4, _3]] // integer division
  implicitly[_2 =:= Mod[_8, _3]]
  implicitly[_3 =:= Gcd[_6, _9]]

  type _11a = Divide[Plus[Power[_3, _4], _7], _8]
  type _11b = Minus[Times[_4, Mod[Times[Gcd[_6, _2], _9], _5]], _1]
  implicitly[_11a =:= _11b]
}

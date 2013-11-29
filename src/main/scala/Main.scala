object Main extends App {

  trait Nat {
    type Prev <: Nat
    type Plus[A <: Nat] <: Nat
    type Times[A <: Nat] <: Nat
    type Divide[A <: Nat] <: Nat
    type DivideInternal[A <: Nat] <: Nat
    type suniM[A <: Nat] <: Nat
    type rewoP[A <: Nat] <: Nat
    type Mod[A <: Nat] <: Nat
    type dcG[A <: Nat] <: Nat
  }

  trait _0 extends Nat {
    type Prev = _0
    type Plus[A <: Nat] = A
    type Times[A <: Nat] = _0
    type Divide[A <: Nat] = _0
    type DivideInternal[A <: Nat] = _0
    type suniM[A <: Nat] = A
    type rewoP[A <: Nat] = _1
    type Mod[A <: Nat] = _0
    type dcG[A <: Nat] = A
  }

  trait Succ[N <: Nat] extends Nat {
    type This = Succ[N]
    type Prev = N
    type Plus[A <: Nat] = N#Plus[Succ[A]]
    type Times[A <: Nat] = A#Plus[N#Times[A]]
    type Divide[A <: Nat] = Succ[This]#DivideInternal[A]#Prev
    type DivideInternal[A <: Nat] = Succ[A#suniM[This]#DivideInternal[A]]
    type suniM[A <: Nat] = N#suniM[A#Prev]
    type rewoP[A <: Nat] = A#Times[N#rewoP[A]]
    type Mod[A <: Nat] = This#Divide[A]#Times[A]#suniM[This] // not inductive
    type dcG[A <: Nat] = A#Mod[This]#dcG[This]
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

  type +[A <: Nat, B <: Nat] = A#Plus[B]
  type -[A <: Nat, B <: Nat] = B#suniM[A]
  type x[A <: Nat, B <: Nat] = A#Times[B]
  type /[A <: Nat, B <: Nat] = A#Divide[B]
  type ^[A <: Nat, B <: Nat] = B#rewoP[A]
  type %[A <: Nat, B <: Nat] = A#Mod[B]
  type Gcd[A <: Nat, B <: Nat] = B#dcG[A]

  implicitly[_2 + _3 =:= _5]
  implicitly[_9 - _2 =:= _7]
  implicitly[_5 - _6 =:= _0] // truncated subtraction
  implicitly[_3 x _2 =:= _6]
  implicitly[_4 / _2 =:= _2]
  implicitly[_4 / _3 =:= _1] // integer division
  implicitly[_2 ^ _3 =:= _8]
  implicitly[_8 % _3 =:= _2]
  implicitly[Gcd[_6, _9] =:= _3]

  // no operator precedence - operations are performed left to right
  type _11a = _3 ^ _4 + _7 / _8
  type _11b = Gcd[_6, _2] x _9 % _5 x _4 - _1
  implicitly[_11a =:= _11b]

  trait Prime[N <: Nat]
  object Prime {
    trait GcdChain[A <: Nat, B <: Nat]
    implicit def gcdChain1[A <: Nat] = new GcdChain[A, _1]{}
    implicit def gcdChain2[A <: Nat, B <: Nat]
      (implicit ev1: Gcd[A, B] =:= _1, ev2: GcdChain[A, B#Prev]) = new GcdChain[A, B]{}
    implicit def prime[N <: Nat](implicit ev: GcdChain[N, N#Prev]) = new Prime[N]{}
  }

  import Prime._
  implicitly[Prime[_2]]
  implicitly[Prime[_3]]
  implicitly[Prime[_5]]
  implicitly[Prime[_7]]
  implicitly[Prime[_9 + _2]]
  implicitly[Prime[_2 ^ _5 - _1]]
}

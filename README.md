# Peano arithmetic

Experimenting with implementing basic mathematical operations using Peano numbers on Scala's type system.

Each number is defined as either `_0` or as a `Succ` (successor):

```scala
trait Nat
trait _0 extends Nat
trait Succ[N <: Nat] extends Nat

type _1 = Succ[_0]
type _2 = Succ[_1]
// ...
```

Examples of what can be proven by the compiler:

```scala
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

  // check primality
  implicitly[Prime[_3]]
  implicitly[Prime[_2 + _9]]
  implicitly[Prime[_2 ^ _5 - _1]]
```

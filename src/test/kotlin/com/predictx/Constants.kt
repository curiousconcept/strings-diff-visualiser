package com.predictx

const val example1_s1: String = "my&friend&Paul has heavy hats! &"
const val example1_s2: String = "my friend John has many many friends &"
const val example1_output: String = "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"

const val example2_s1: String = "mmmmm m nnnnn y&friend&Paul has heavy hats! &"
const val example2_s2: String = "my frie n d Joh n has ma n y ma n y frie n ds n&"
const val example2_output: String = "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"

// Bug in the specs sheet? 'r' should be before 't' as in alphabet
//const val example3_s1: String = "Are the kids at home? aaaaa fffff"
//const val example3_s2: String = "Yes they are here! aaaaa fffff"
//const val example3_output: String = "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh"
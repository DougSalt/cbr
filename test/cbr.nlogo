extensions [ cbr table ]

breed [ agents-a agent-a ]
breed [ agents-b agent-b ]
;breed [ agents-c agent-c ]
breed [ agents-d agent-d ]

to run-tests

  output-print "cbr: Runing tests..."
  let case-base cbr:new

  test cbr:gt "greater than" "gt"
  test cbr:lt "less than" "lt"
  test cbr:eq "equal" "eq"
  test cbr:incmp "incomparable" "incmp"

  output-print "Testing string comparator..."

  let string-a "aaaaaa"
  let string-b "bbbbbb"
  let string-c "aaaacc"
  let string-d "dddddd"

  let a cbr:add case-base string-a "install" true
  let b cbr:add case-base string-b"install" true
  let c cbr:add case-base string-c "install" true
  let d cbr:add case-base string-d "install" true

  test cbr:eq? case-base b b b  true "eq? STRING true"
  test cbr:eq? case-base c b b false "eq? STRING false"
  test cbr:lt? case-base b a c false "lt? STRING false"
  test cbr:lt? case-base a b c true "lt? STRING true"
  test cbr:gt? case-base a b c false "gt? STRING false"
  test cbr:gt? case-base b a c true "gt? STRING true"
  test cbr:incmp? case-base a b d true "incmp? STRING true"
  test cbr:incmp? case-base b a c false "incmp? STRING false"

  test cbr:state case-base cbr:match case-base "dd" "install" string-d "match STRING"
  let d2 cbr:add case-base "ddd" "install" true
  test length cbr:matches case-base "dd" "install" 2 "matches STRING"

  output-print "Testing list comparator..."

  set case-base cbr:new

  let some-state-1 ["state-1" "state-2" "state-3"]
  let some-state-2 ["state-1" "state-4" "state-5"]
  let some-state-3 ["state-1" "state-2" "state-6"]
  let some-state-4 ["state-1" "state-2" "state-3"]
  let some-decision-1 ["install"]
  let some-decision-2 ["install"]
  let some-decision-3 ["install"]
  let some-decision-4 ["install"]
  let some-outcome-1 [true true true]
  let some-outcome-2 [true true false]
  let some-outcome-3 [true false true]
  let some-outcome-4 [true false false]

  let states (list some-state-1 some-state-2 some-state-3 some-state-4 )
  let decisions (list some-decision-1 some-decision-2 some-decision-3 some-decision-4 )
  let outcomes (list some-outcome-1 some-outcome-2 some-outcome-3 some-outcome-4 )
  reset-ticks


  tick
  let some-case-1 cbr:add case-base some-state-1 some-decision-1 some-outcome-1
  tick
  let some-case-2 cbr:add case-base some-state-2 some-decision-2 some-outcome-2
  tick
  let some-case-3 cbr:add case-base some-state-3 some-decision-3 some-outcome-3
  tick
  let some-case-4 cbr:add case-base some-state-4 some-decision-4 some-outcome-4

  let some-invalid-case cbr:add case-base ["state-1" "state-2"] some-decision-3 some-outcome-3

  test cbr:state case-base some-case-1 some-state-1 "state LIST"
  test cbr:outcome case-base some-case-1 some-outcome-1 "outcome LIST"
  test cbr:decision case-base some-case-1 some-decision-1 "decision LIST"
  test cbr:get-time case-base some-case-1 "1.0" "get-time LIST"


  test cbr:eq? case-base some-case-1 some-case-1 some-case-1 true "eq? LIST true"
  test cbr:eq? case-base some-case-1 some-case-2 some-case-1 false "eq? LIST false"
  test cbr:lt? case-base some-case-1 some-case-2 some-case-3 true "lt? LIST true"
  test cbr:lt? case-base some-case-2 some-case-1 some-case-3 false "lt? LIST false"
  test cbr:gt? case-base some-case-2 some-case-1 some-case-3 true "gt? LIST true"
  test cbr:gt? case-base some-case-1 some-case-2 some-case-3 false "gt? LIST false"
  test cbr:incmp? case-base some-case-1 some-case-2 some-case-3 false "incmp? LIST false"
  test cbr:incmp? case-base some-case-1 some-case-2 some-invalid-case false "incmp? LIST true"

  ; Remove the dodgy case

  cbr:remove case-base some-invalid-case

  ; Doing a single match

  let my-match cbr:match case-base (list "state-1" "state-2" "state-6") "install"

  test cbr:outcome case-base my-match some-outcome-3 "cbr:match cbr:outcome"
  test cbr:state case-base my-match some-state-3 "cbr:match cbr:state"
  test cbr:decision case-base my-match some-decision-3 "cbr:match cbr:decision"

  ; Ranking

  cbr:set-rank case-base some-case-4 99
  test cbr:get-rank case-base some-case-4 "99.0" "set-rank"

  ; Doing multiple matches

  let my-matches cbr:matches case-base (list "state-1" "state-2" "state-3") "install"
  test length my-matches 1 "matches"

  cbr:set-rank case-base some-case-4 0
  test cbr:get-rank case-base some-case-4 "0.0" "set-rank"

  set my-matches cbr:matches case-base (list "state-1" "state-2" "state-3") "install"
  test length my-matches 2 "matches"

  ; Setting Time

  cbr:set-time case-base some-case-1 200
  test cbr:get-time case-base some-case-1 "200.0" "time"

  ; Listing

  foreach cbr:all case-base [ this-case ->
    test member? cbr:decision case-base this-case decisions true  (word "cbr:decision on " cbr:decision case-base this-case)
    test member? cbr:state case-base this-case states true  (word "cbr:state on " cbr:state case-base this-case)
    test member? cbr:outcome case-base this-case outcomes true  (word "cbr:outcome on " cbr:outcome case-base this-case)
  ]

  ; Resizing

  cbr:set-max-size case-base 3

  test length cbr:all case-base 4 "all should return 4"

  cbr:resize case-base
  test length cbr:all case-base 3 "all should return 3 after cbr:resize"

  ; Forgetting

  cbr:set-earliest case-base 3
  test cbr:get-earliest case-base 3 " cbr:set-earliest and cbr-get-earliest"

  test length cbr:all case-base 3 "all should return 3"
  cbr:forget case-base
  test length cbr:all case-base 2 "all should return 2"

  output-print "Testing numeric comparator..."

  set case-base cbr:new

  let double-a 1.0
  let double-b 2.55
  let double-c pi
  let double-d e

  set a cbr:add case-base double-a "install" true
  set b cbr:add case-base double-b "install" true
  set c cbr:add case-base double-c "install" true
  set d cbr:add case-base double-d "install" true

  test cbr:eq? case-base b b b  true "eq? DOUBLE true"
  test cbr:eq? case-base c b b false "eq? DOUBLE false"
  test cbr:lt? case-base a b c false "lt? DOUBLE false"
  test cbr:lt? case-base b a c true "lt? DOUBLE true"
  test cbr:gt? case-base b a c false "gt? DOUBLE false"
  test cbr:gt? case-base a b c true "gt? DOUBLE true"
  test cbr:incmp? case-base a b d false "incmp? DOUBLE false"
  set d2 cbr:add case-base "string" "install" true
  test cbr:incmp? case-base b a d2 true "incmp? DOUBLE true"
  cbr:remove case-base d2

  test cbr:state case-base cbr:match case-base pi "install" double-c "match DOUBLE"
  set d2 cbr:add case-base pi "install" true
  test length cbr:matches case-base pi "install" 2 "matches DOUBLE"

  output-print "Testing agent comparator..."

  set case-base cbr:new

  clear-turtles
  create-agents-a 2 [ set color grey ]
  create-agents-b 3 [ set color grey ]
  let agents-c (turtle-set agents-a agents-b)
  create-agents-d 2 [ set color grey ]

  set a cbr:add case-base agents-a "install" true
  set b cbr:add case-base agents-b "install" true
  set c cbr:add case-base agents-c "install" true
  set d cbr:add case-base agents-d "install" true

  test cbr:eq? case-base b b b true "eq? AGENT true"
  test cbr:eq? case-base a b b false "eq? AGENT false"
  test cbr:lt? case-base b a c true "lt? AGENT true"
  test cbr:lt? case-base a b c false "lt? AGENT false"
  test cbr:gt? case-base a b c true "gt? AGENT true"
  test cbr:gt? case-base b a c false "gt? AGENT false"
  test cbr:incmp? case-base a a a false "incmp? AGENT false"
  let invalid cbr:add case-base "string" "install" true
  test cbr:incmp? case-base b a invalid true "incmp? AGENT true"
  cbr:remove case-base invalid

  test cbr:state case-base (cbr:match case-base agents-d "install") agents-d "match AGENT"
  let d1 cbr:add case-base agents-d "install-d1" true
  set d2 cbr:add case-base agents-d "install-d2" true
  test length (cbr:matches case-base agents-d "install") 3 "matches AGENT"


  output-print "Testing Ben's data..."


  let grass-table table:make
  table:put grass-table "fertiliser"  true
  table:put grass-table "plough" false
  table:put grass-table "seed" true
  table:put grass-table "harbicide" false
  table:put grass-table "tractor" true
  table:put grass-table "cow" false
  table:put grass-table "bull" false
  table:put grass-table "ewe" false
  table:put grass-table "tup" false

  let wheat-table table:make
  table:put wheat-table "fertiliser"  true
  table:put wheat-table "plough" true
  table:put wheat-table "seed" true
  table:put wheat-table "harbicide" true
  table:put wheat-table "tractor" true
  table:put wheat-table "cow" false
  table:put wheat-table "bull" false
  table:put wheat-table "ewe" false
  table:put wheat-table "tup" false

  let barley-table table:make
  table:put barley-table "fertiliser"  true
  table:put barley-table "plough" true
  table:put barley-table "seed" true
  table:put barley-table "harbicide" true
  table:put barley-table "tractor" true
  table:put barley-table "cow" false
  table:put barley-table "bull" false
  table:put barley-table "ewe" false
  table:put barley-table "tup" false

  let beef-table table:make
  table:put beef-table "fertiliser"  false
  table:put beef-table "plough" false
  table:put beef-table "seed" false
  table:put beef-table "harbicide" false
  table:put beef-table "tractor" true
  table:put beef-table "cow" true
  table:put beef-table "bull" true
  table:put beef-table "ewe" false
  table:put beef-table "tup" false

  let sheep-table table:make
  table:put sheep-table "fertiliser"  false
  table:put sheep-table "plough" false
  table:put sheep-table "seed" false
  table:put sheep-table "harbicide" false
  table:put sheep-table "tractor" true
  table:put sheep-table "cow" false
  table:put sheep-table "bull" false
  table:put sheep-table "ewe" true
  table:put sheep-table "tup" true



  ;; build the first test case-bases

  let bens-case-base cbr:new
  let grass cbr:add bens-case-base grass-table "equation" "grass"
  let wheat cbr:add bens-case-base wheat-table "equation" "wheat"
  let barley cbr:add bens-case-base barley-table "equation" "barley"
  let beef cbr:add bens-case-base beef-table "equation" "beef"
  let sheep cbr:add bens-case-base sheep-table "equation" "sheep"


  ; build the test case
  let bens-test-table table:make
  table:put bens-test-table "fertiliser"  false
  table:put bens-test-table "plough" false
  table:put bens-test-table "seed" false
  table:put bens-test-table "harbicide" false
  table:put bens-test-table "tractor" true
  table:put bens-test-table "cow" false
  table:put bens-test-table "bull" false
  table:put bens-test-table "ewe" true
  table:put bens-test-table "tup" true

  cbr:lambda bens-case-base bens-comparator

  test cbr:outcome bens-case-base (cbr:match bens-case-base bens-test-table "equation") "sheep" "match BEN'S single case"
  test cbr:outcome bens-case-base (item 0 (cbr:matches bens-case-base bens-test-table "equation")) "sheep" "matches BEN'S single case"

  set bens-test-table table:make
  table:put bens-test-table "fertiliser"  true
  table:put bens-test-table "plough" true
  table:put bens-test-table "seed" true
  table:put bens-test-table "harbicide" true
  table:put bens-test-table "tractor" true
  table:put bens-test-table "cow" false
  table:put bens-test-table "bull" false
  table:put bens-test-table "ewe" false
  table:put bens-test-table "tup" false

  test cbr:outcome bens-case-base (cbr:match bens-case-base bens-test-table "equation") "wheat" "match BEN'S double case"
  test (sort map [ case -> cbr:outcome bens-case-base case ] cbr:matches bens-case-base bens-test-table "equation") ["barley" "wheat"] "matches BEN's double case"

  set bens-test-table table:make
  table:put bens-test-table "fertiliser"  false
  table:put bens-test-table "plough" false
  table:put bens-test-table "seed" false
  table:put bens-test-table "harbicide" false
  table:put bens-test-table "tractor" true
  table:put bens-test-table "cow" false
  table:put bens-test-table "bull" false
  table:put bens-test-table "ewe" false
  table:put bens-test-table "tup" false

  test cbr:outcome bens-case-base (cbr:match bens-case-base bens-test-table "equation") "grass" "match BENS AGENT"
  test (sort map [ case -> cbr:outcome bens-case-base case ] cbr:matches bens-case-base bens-test-table "equation") ["beef" "grass" "sheep"] "matches BEN's double case"
  foreach cbr:matches bens-case-base bens-test-table "equation" [ case ->
    output-print cbr:outcome bens-case-base case
  ]

  output-print "cbr: ...ending tests."
  stop
end

to-report bens-comparator [ some-case-base src-case obj-case ref-case ]

  let src-state cbr:state some-case-base src-case
  let src-decision cbr:decision some-case-base src-case
  let src-hits 0

  let obj-state cbr:state some-case-base obj-case
  let obj-decision cbr:decision some-case-base obj-case
  let obj-hits 0

  let ref-state cbr:state some-case-base ref-case
  let ref-decision cbr:decision some-case-base ref-case


  ;; get common keys in the tables
  let src-ref reduce intersect (list table:keys src-state table:keys ref-state)
  let obj-ref reduce intersect (list table:keys obj-state table:keys ref-state)


  ;; score +2 for every common key
  set src-hits 2 * length src-ref
  set obj-hits 2 * length obj-ref

  ;; if any common keys:
  ;; score +1 for equal values for each common key
  if length src-ref > 0 [
    foreach src-ref [ i ->
      if table:get ref-state i = table:get src-state i [
        set src-hits src-hits + 1
      ]
    ]
  ]

  if length obj-ref > 0 [
    foreach obj-ref [ i ->
      if table:get ref-state i = table:get obj-state i [
        set obj-hits obj-hits + 1
      ]
    ]
  ]



  if src-decision != ref-decision
  and obj-decision = ref-decision [
    ;show "<>gt"
    report cbr:gt
  ]

  if  src-decision != ref-decision [
    ;show "incmp"
    report cbr:incmp
  ]

  if  obj-decision != ref-decision [
    ;show "<>lt"
    report cbr:lt
  ]

  (ifelse
    src-hits > obj-hits [
      ;show "lt"
      report cbr:lt
    ]
    src-hits < obj-hits [
      ;show "gt"
      report cbr:gt
    ]
    src-hits = obj-hits [
      ;show "gt"
      report cbr:eq
    ]
    [
      ;show "eq"
      report cbr:incmp
    ]
  )
end

; https://stackoverflow.com/questions/26928738/find-lists-intersection-in-netlogo
to-report intersect [a b]
  report (filter [x -> member? x b ] a)
end


to test [actual-value expected-value message]
  if actual-value != expected-value [
    error (word "cbr: "
      message
      " expected: " expected-value
      " got: " actual-value
    )
  ]
end
@#$#@#$#@
GRAPHICS-WINDOW
210
10
251
52
-1
-1
1.0
1
10
1
1
1
0
1
1
1
-16
16
-16
16
0
0
1
ticks
30.0

BUTTON
8
17
106
50
NIL
run-tests
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

OUTPUT
117
66
652
221
12

BUTTON
40
113
110
146
Clear
clear-output
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

@#$#@#$#@
# CBR Extension

## WHAT IS IT?

This is an example of how to use the case based reasoning plugin.

## HOW IT WORKS

The code implements a reference version of code using the extension that is described here.

Select the run-testing button to make sure that the code is behaving as it should. If there is no error dialogue then the code is working as expected. The code also shows how to the use the extension.

Normally a  case base consists of a series of cases, each of these cases consist of

+ state
+ decision/activity
+ outcome

The state can be anything such as the bank balance of the agent. The decision/activity might be to install central heating. The outcome might be straight forwardly yes or no. It might be probability, or it might some arbitrary decision/activity metric for use elsewhere.

A state and decision/activity are presented to the case base. The case base is searched for the closest match (if there is one) and the outcome of that match is given.

A NetLogo case consists of a state in any of the standard Netlogo variables, such as list, number, string, etc. This is strictly defined by the `cbr:lambda` which is the comparator program used to determine the "distance" the three cases:

+ case A
+ case B
+ referent case R

are relative to ecah other. That is, if: 

+ the case A is 'closer' to the referent case R than the case B using `cbr:lamda`  to the referent case R then `cbr:lt` is returned
+ the case B is 'closer' to the referent case R than the case A using `cbr:lamda`  to the referent case R then `cbr:gt` is returned
+ the case B is 'same distance' to the referent case R than the case A using `cbr:lamda`  to the referent case R then `cbr:eq` is returned
+ the case B is 'closer' using `cbr:lamda`  to the referent case R then `cbr:lt` is returned

The `cbr:lamda` is then used by `cbr:match` or `cbr:matches` to determine a single closest match of a "hypothetical" case that is presented to `cbr:match` or more or one equally close matches if `cbr:matches` is used. The case will consists of a state and decison and returning the 'best match' base on the repeated use of `cbr:lamda` to determine which is the closest match. If the match does not meet a suitable criterion then `cbr:incmp` is returned.

When using `cbr:match`, then if there are more than one match then the case with the highest rank is returned, or failing that the oldest.  If both these conditions are equal then the result is a random selection from among those cases. More than one case may be returned if `cbr:matches` is used.

If no `cbr:lamba` is provided then a default lambda is used. The specification for this is as follows.

The comparison algorithm always has three arguments:

+ Case A
+ Case B
+ Referent case R

Cases A and B need not be in a case base; they may also have empty Decision and Outcome parts, or simply be a State. Case R may have empty Decision and Outcome parts, or simply be a State. (In a typical use-case, R is an ‘expected state of the world’ for which the deciding agent is trying to find a range of cases.)

The algorithm returns one of four responses:

+ `cbr:gt` – iff A more similar to R than B
+ `cbr:lt` – iff B more similar to R than B
+ `cbr:eq` – iff A and B are equally similar to R
+ `cbr:incmp` – iff A and B are not comparably similar to R

The algorithm should do the following:

1. If R is a case, then set R to the State part of the case
2. If A is a case, then set A to the State part of the case
3. If B is a case, then set B to the State part of the case
4. We now consider different datatypes for R, A and B in S = { number } and M = { string, patch, turtle, link, list, agent set } a N. B. S is a set of scalar datatypes; M is a set of multidimensional datatypes
5. If R, A and B are not the same datatype, return `cbr:incmp`
6. If R, A and B are all numbers, then if abs (R – A) &lt; abs(R – B) return `cbr:gt`; else if abs (R – A) &gt; abs(R – B) return `cbr:lt`; else return `cbr:eq`
7. If R, A and B are all agent sets, then let Am = A – (A intersect B) and let Bm = B – (A intersect B). If Am and Bm are both empty, return `cbr:eq`.  Let AmR = Am – (Am intersect R); Let BmR = Bm – (Bm intersect R); Let AR = A intersect R; Let BR = B intersect R. If AR is a superset of BR and AmR is a subset of BmR, return `cbr:gt`; Else if BR is a superset of AR and BmR is a subset of AmR, return `cbr:lt`; Else return `cbr:incmp`
   **Basic principle:** A is more similar to R than B is if all agents that B has in common with R, A also has in common with R, and all agents that A does not have in common with R, B also does not have in common with R. If there are members of R that are members of A but not B, and there are also members of R that are members of B but not A, then A and B are incomparably similar to R; likewise, if we look at members of A and B that are not members of R, unless one is a subset-of-or-equal-to the other, A and B are incomparably similar. The diagram below shows the three conditions in which the agent set comparisons are not incomparable. Note that in the left one, if B – (A intersect B) is empty, A is still more similar than B to R; also, if A – (A intersect B) is empty, A is still more similar to R. If both those regions are empty, then A and B are equal. The corresponding points are true of the middle diagram (i.e. with A and B swapped).
  ![Intersection diagrams for the default comparator](../doc/default-comparator.png)
8. If A, B and R are all lists, then let answer = `cbr:eq`. For i in 1:min(length of A, length of B, length of R): if A[i] == R[i] and B[i] != R[i], then if answer == `cbr:lt`, return `cbr:incmp`; else let answer = `cbr:gt`. Else if B[i] == R[i] and A[i] != R[i], then if answer == `cbr:gt`, return `cbr:incmp`; else let answer = `cbr:lt`. End For. If answer == `cbr:gt` and A has the shortest length and and For all j in (i + 1):min(length of B, length of R), B[j] != R[j], then return `cbr:gt`. If answer == `cbr:lt` and B has the shortest length and For all j in (i + 1):min(length of A, length of R), A[j] != R[j], then return `cbr:lt`. If A and B have different lengths, return `cbr:incmp`. If For all j in (i + 1):(length of A), A[j] == B[j], then return `cbr:eq`ual else return `cbr:incmp`
  **Basic principle:** A is more similar-to R than B if everywhere that B is equal to R, A is also equal to R
9. If A, B and R all being strings can work on the same basis as lists, with i and j indexing characters.
10. If A, B and R are all patches then they can also be handled similarly to lists – each of the default and declared patches-own variables would be put into the lists in the same order.

## HOW TO USE IT

The primitives available are:

### cbr:new

Instantiates a case base object.

#### Parameters

None

#### Returns

+ case base object 

#### cbr:add

#### Parameters

Adds a case the the NetLogo case base object. The tick at which this takes place is also recorded and can be accessed using `cbr:get-time`. This property can be manipulated by using`cbr:set-time`

Note the case object cannot exist independently of the case base object. If the latter is destroyed or removed, then so to is the former.

+ case base object 

+ state (in a form that `cbr:lamda` requires for doing the "distance" function.

+ decision/activity (in a form that `cbr:lamda` requires for doing the "distance" function.

+ outcome (in any form that you require, providing this is not used as part of the decison making in `cbr:lamda`).

#### Returns

Returns a case object. This is not really useful, but can be used for display and debugging uses if needs be.

### cbr:combine

All the cases from the second case base are appended to the case base in the first argyument

#### Parameters

+ a to case base object 
+ a from case base oject

#### Returns

Nothing.

### cbr:all

Used to return all the cases in a specific case base.

#### Parameters

+ case base object 

#### Returns

A NetLogo list of all the cases in the case base object.

### cbr:remove

Removes a particular case from a case base.

#### Parameters

+ case base object 

+ case object (returned when the case is created)

#### Returns

Nothing.

### cbr:state

This is a reporter on the state contained within a case object.

#### Parameters

+ case base object 

+ case object

#### Returns

A state in the form the `cbr:lamda` uses. In the example code showed above this takes the form of a list of strings.

### cbr:decision

This is a reporter on the decision/activity contained within a case object.

#### Parameters

+ case base object 

+ case object

#### Returns

A decision in the form the `cbr:lamda` uses. In the example code showed above this takes the form of a string.

### cbr:outcome

This is a reporter on the outcome of a case.

#### Parameters

+ case base object 

+ case object

#### Returns

In the example code showed above this takes the form of a list of booleans.

### cbr:set-time

Time is used in conjunction `cbr:forget` to remove aged cases from the case base. The time is 0 or a postive number and corresponds to the number of ticks.

#### Parameters

+ case base object 

+ case object (returned when the case is created)

+ number of ticks representing when the case was entered into the case base table.

#### Returns

Nothing.

### cbr:get-time

Obtains/gets the time of origin on a particular case within a case base object. This is measured in number of ticks, is 0 or positive.  Time is used in conjunction `cbr:forget` to remove aged cases from the case base.

#### Parameters

+ case base object 

+ case object within the above case base

#### Returns

A number of ticks, representing when the case was added to the case base. This is generally the current cclick but may be manipulated using `cbr:set-time`.

### cbr:set-earliest

This sets the earliest tick. If a case base has a creation time (as a tick) older than this, then upon the evocaction of the next `cbr:forget` then this case will be deleted, i.e. *forgotten*.

#### Parameters

+ case base object 

+ number representing the oldest tick, before which if any case is stamped with an earlier time then these cases will be removed upon invocation of `cbr:forget`. The number in this case corresponds to the number of ticks.

#### Returns

Nothing.

### cbr:forget

If the `cbr:set-earliest` has been used, then `cbr:forget` will enforce this limit. We have done it this way to prevent exceptions being thrown if the this limit is exceeded, thus it requires explicit invocation via the user to make the case base forget.

A tick is used to denote time. A NetLogo case base will have as part of its metadata the time before which all cases acquired at that time will be forgotten. This property may be obtained using the `cbr:get-earliest` and by default is set to 0.

The actual age of a case within a casebase can be manipulated with the use of `cbr:set-time` and `cbr:get-time`.

### cbr:lambda

This may the reporter that does distance comparison. This is never called directly
If a `cbr:lambda` is not provided, then the default comparator is called. The default comparator has the following specification.

This is the comparator used in the example program to which this documentation is attached.


#### Parameters

+ case base object 

+ An anonymous reporter or named reporter, which must take four parameters 

    - case base object
    - case A
    - case B
    - referent case R

#### Returns

This *must* return one of the following

+ `cbr:lt`
+ `cbr:gt`
+ `cbr:incmp`
+ `cbr:eq`

The current represantion that `cbr:lamda` must return if of the three cases

+ case A;
+ case B, and
+ referent case R,

when the case A and the case B "are an equal distance" from the referent case R.

### cbr:eq

This is used to standardise response from `cbr:lamda` so `cbr:match` and `cbr:matches` will work correctly with it.

#### Parameters

The current represantion that `cbr:lamda` must return if of the three cases

+ case A;
+ case B, and
+ referent case R,

when the case A is "equal"  distance from the referent case R as the case B.

### cbr:gt

This is used to standardise response from `cbr:lamda` so `cbr:match` and `cbr:matches` will work correctly with it.

#### Parameters

None

#### Returns

The current represantion that `cbr:lamda` must return if of the three cases

+ case A;
+ case B, and
+ referent case R,

+ case A;
+ case B, and
+ referent case R,

when the case A is "further" from the referent case R than the case B.

### cbr:lt

This is used to standardise response from `cbr:lamda` so `cbr:match` will work correctly with it.

#### Parameters

None	
#### Returns

The current represantion that `cbr:lamda` must return if of the three cases

+ case A;
+ case B, and
+ referent case R,

when the case A is "closer" to the referent case R than the case B.

### cbr:incmp

#### Parameters

+ None

#### Returns

The current represantion that `cbr:lamda` must return if any of  the three cases

+ case A;
+ case B, and
+ referent case R,

cannot be used for comparison.

### cbr:get-earliest

This gets the earliest time at which a case in a casebase will have been created at. Before this tick if `cbr:forget` is called then all cases with the casebase that have a time stamp (when they were notionally create) less than this value, then these cases will be deleted from the case base.

#### Parameters

+ case base object 

#### Returns

+ A number which is the maxiumum number of cases the case base can contain.  

### cbr:set-earliest

This gets the earliest time at which a case in a casebase will have been created at. Before this tick if `cbr:forget` is called then all cases with the casebase that have a time stamp (when they were notionally create) less than this value, then these cases will be deleted from the case base.

#### Parameters

+ case base object 

+ A number representing a tick. 

#### Returns

Nothing.

### cbr:set-max-size

This gets the maxium size for the case base. That is the maxiumum number of cases it may contain. The case base may actually contain more cases than this number and it requires the specific invocation of  `cbr:resize` to enforce this size restriction. Upon the invocation of `cbr:resize` those case with the lowest rank will be deleted. If there are two cases with equal rank then the oldest will then be deleted.

#### Parameters

+ case base object 

+ A number which is the maxiumum number of cases the case base can contain.  

#### Returns

Nothing.

### cbr:get-max-size

This sets the maxium size for the case base. That is the maxiumum number of cases it may contain. The case base may actually contain more cases than this number and it requires the specific invocation of  `cbr:resize` to enforce this size restriction. Upon the invocation of `cbr:resize` those case with the lowest rank will be deleted. If there are two cases with equal rank then the oldest will then be deleted.

#### Parameters

+ case base object 

+ A number which is the maxiumum number of cases the case base can contain.  

#### Returns

Nothing.

### cbr:set-rank


This sets the rank on a particular case in a case base. When choosing between two cases when decreasing the size of a case base to a specified maximum number of cases, then to determine which cases should be deleted to reduce the size of the case base, we use the concept of the rank. All cases start with 0 rank. However, this rank can be set using this primitive, and any case with a higher rank will be preferred when retaining cases.
#### Parameters

+ case base object 

+ a number representing the rank of the case. The higher the rank, the more likely it is that the case will survive.

#### Returns

Nothing.

### cbr:get-rank

This gets the rank on a particular case in a case base. When choosing between two cases when decreasing the size of a case base to a specified maximum number of cases, then to determine which cases should be deleted to reduce the size of the case base, we use the concept of the rank. All cases start with 0 rank. However, this rank can be set using this primitive, and any case with a higher rank will be preferred when retaining cases.

#### Parameters

+ case base object 

#### Returns

Nothing.

### cbr:resize

This resizes the case base to the number of cases specified by `cbr:set-max-size`. When choosing between two cases when decreasing the size of a case base to a specified maximum number of cases, then to determine which cases should be deleted to reduce the size of the case base, we use the concept of the rank. All cases start with 0 rank. However, this rank can be set using this primitive, and any case with a higher rank will be preferred when retaining cases.

This command is explicit. It has been done this way for two reasons. Firstly this gives control to the user and secondly the only way to interrupt the work flow in NetLogo appears to be some kind of exception. This, of course means that the number of cases in a case base can exceed this number. Again this is intentional and the number can be checked using the code `length cbr:all some-case-base`.

#### Parameters

+ case base object 

#### Returns

Nothing.

### cbr:match

This returns the closet matched case that matches the state in the state parameter and the decision/activity that has been selected.

#### Parameters

+ case base object 

+ state in a format that can be use by `cbr:lambda` to do the distance assessment between cases, which describes the necessary state of the agent)

+ decision/activity in a format that can be use by `cbr:lambda` to do the distance assessment between cases, which describes the decision or activity that is the agent is about to make or perform).

#### Returns

Nobody if there is no match otherwise returns a match case from the case base, which then may be interrogated using

+ `cbr:state`
+ `cbr:decision`
+ `cbr:outcome`

The first two can be used to validate the case the last is usually what we are after in terms of makeing a decision.

This will return only one case based on the selection criteria mentioned in the "HOW IT WORKS" section above.

### cbr:matches

This returns the set of closest matched cases that match the state in the state parameter and the decision/activity that has been selected.

#### Parameters

+ case base object 

+ state in a format that can be use by `cbr:lambda` to do the distance assessment between cases, which describes the necessary state of the agent)

+ decision/activity in a format that can be use by `cbr:lambda` to do the distance assessment between cases, which describes the decision or activity that is the agent is about to make or perform).

#### Returns

Nobody if there is no match otherwise returns a list of cases from the case base, which then may be interrogated using

+ `cbr:state`
+ `cbr:decision`
+ `cbr:outcome`

The first two can be used to validate the case the last is usually what we are after in terms of makeing a decision.

## THINGS TO NOTICE

(suggested things for the user to notice while running the model)

## THINGS TO TRY

The typical workflow is 

1. instantiate a case base using `cbr:new`
2. add some cases to it using `cbr:add`
3. query the case base using either `cbr:match` or `cbr:matchess` to return one or more cases
4. Interrogate the returned case outcome to see what the case base recommends.

In addition to this you might to add the resultant state, decision and outcome back into the case base in order to emulate memory.


## EXTENDING THE MODEL

As mentioned above you can write your own default comparator. Here is an example of how to do this.

```
to-report new-comparator [some-case-base yes-case no-case reference-case]
  let yes-state cbr:state some-case-base yes-case
  let no-state cbr:state some-case-base no-case
  let reference-state cbr:state some-case-base reference-case

  if (cbr:decision some-case-base yes-case != cbr:decision some-case-base no-case and
    cbr:decision some-case-base yes-case != cbr:decision some-case-base reference-case) [
    report cbr:incmp
  ]
  if not is-list? yes-state or not is-list? no-state or not is-list? reference-state [
    report cbr:incmp
  ]
  if length yes-state != length no-state  [
    report cbr:incmp
  ]
  if length no-state != length reference-state [
    report cbr:incmp
  ]
  if (item 0 yes-state = item 0 no-state and
      item 1 yes-state = item 1 no-state and
      item 2 yes-state = item 2 no-state) [
    report cbr:eq
  ]

  let comparison-1-and-3 0
  foreach n-values (length yes-state) [i -> i] [ i ->
    if item i yes-state = item i reference-state [
      set comparison-1-and-3 comparison-1-and-3 + 1
    ]
  ]
  let comparison-2-and-3 0
  foreach n-values (length no-state) [i -> i] [ i ->
    if item i no-state = item i reference-state [
      set comparison-2-and-3 comparison-2-and-3 + 1
    ]
  ]
  if comparison-1-and-3 = 0 and comparison-2-and-3 = 0 [
    report cbr:incmp
  ]
  if comparison-1-and-3 > comparison-2-and-3 [
    report cbr:lt
  ]
  report cbr:gt
end
```

This example makes sure the decision is the same in all the matched cases as well. The defautl comparator does not do this.

Once this is implemented then it can loaded against a particular case base by using the command:

```
cbr:lambda new-comparator  

```
## NETLOGO FEATURES

(interesting or unusual features of NetLogo that the model uses, particularly in the Code tab; or where workarounds were needed for missing features)

## RELATED MODELS

achsium.nlogo uses this case base reasoner.

## CREDITS AND REFERENCES

Author doug.salt@hutton.ac.uk (see https://gitlab.com/doug.salt/cbr for the repository).
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

sheep
false
15
Circle -1 true true 203 65 88
Circle -1 true true 70 65 162
Circle -1 true true 150 105 120
Polygon -7500403 true false 218 120 240 165 255 165 278 120
Circle -7500403 true false 214 72 67
Rectangle -1 true true 164 223 179 298
Polygon -1 true true 45 285 30 285 30 240 15 195 45 210
Circle -1 true true 3 83 150
Rectangle -1 true true 65 221 80 296
Polygon -1 true true 195 285 210 285 210 240 240 210 195 210
Polygon -7500403 true false 276 85 285 105 302 99 294 83
Polygon -7500403 true false 219 85 210 105 193 99 201 83

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

wolf
false
0
Polygon -16777216 true false 253 133 245 131 245 133
Polygon -7500403 true true 2 194 13 197 30 191 38 193 38 205 20 226 20 257 27 265 38 266 40 260 31 253 31 230 60 206 68 198 75 209 66 228 65 243 82 261 84 268 100 267 103 261 77 239 79 231 100 207 98 196 119 201 143 202 160 195 166 210 172 213 173 238 167 251 160 248 154 265 169 264 178 247 186 240 198 260 200 271 217 271 219 262 207 258 195 230 192 198 210 184 227 164 242 144 259 145 284 151 277 141 293 140 299 134 297 127 273 119 270 105
Polygon -7500403 true true -1 195 14 180 36 166 40 153 53 140 82 131 134 133 159 126 188 115 227 108 236 102 238 98 268 86 269 92 281 87 269 103 269 113

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270
@#$#@#$#@
NetLogo 6.3.0
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 0.0 1.0
0.0 1 1.0 0.0
0.2 0 0.0 1.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180
@#$#@#$#@
0
@#$#@#$#@

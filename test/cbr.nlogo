extensions [ cbr table ]

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
  test cbr:lt? case-base b a c true "lt? STRING true"
  test cbr:lt? case-base a b c false "lt? STRING false"
  test cbr:gt? case-base a b c true "gt? STRING true"
  test cbr:gt? case-base b a c false "gt? STRING false"
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
  test cbr:lt? case-base some-case-1 some-case-2 some-case-3 false "lt? LIST false"
  test cbr:lt? case-base some-case-2 some-case-1 some-case-3 true "lt? LIST ture"
  test cbr:gt? case-base some-case-2 some-case-1 some-case-3 false "gt? LIST false"
  test cbr:gt? case-base some-case-1 some-case-2 some-case-3 true "gt? LIST true"
  test cbr:incmp? case-base some-case-1 some-case-2 some-case-3 false "incmp? LIST false"
  test cbr:incmp? case-base some-case-1 some-case-2 some-invalid-case false "incmp? LIST true"

  ; Remove the dodgy case

  cbr:remove case-base some-invalid-case

  ; Doing a single match

  let my-match cbr:match case-base (list "state-1" "state-2" "state-6") "install"

  show my-match
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

  output-print "Testing list comparator..."

  set case-base cbr:new

  let double-a 1.0
  let double-b 2.55
  let double-c pi
  let double-d e

  set a cbr:add case-base double-a "install" true
  set b cbr:add case-base double-b"install" true
  set c cbr:add case-base double-c "install" true
  set d cbr:add case-base double-d "install" true

  test cbr:eq? case-base b b b  true "eq? STRING true"
  test cbr:eq? case-base c b b false "eq? STRING false"
  test cbr:lt? case-base b a c true "lt? STRING true"
  test cbr:lt? case-base a b c false "lt? STRING false"
  test cbr:gt? case-base a b c true "gt? STRING true"
  test cbr:gt? case-base b a c false "gt? STRING false"
  test cbr:incmp? case-base a b d true "incmp? STRING true"
  test cbr:incmp? case-base b a c false "incmp? STRING false"

  test cbr:state case-base cbr:match case-base "dd" "install" double-d "match STRING"
  set d2 cbr:add case-base pi "install" true
  test length cbr:matches case-base "dd" "install" 2 "matches STRING"

  output-print "cbr: ...ending tests."
  stop


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

Select the run-testing button to make sure that the code is behaving as it should. If there is no error dialogue then the code is working as expected. The code also shows how to the use the extension.

Normally a  case base consists of a series of cases, each of these cases consist of

+ state
+ decision/activity
+ outcome

The state can be anything such as the bank balance of the agent. The decision/activity might be to install central heating. The outcome might be straight forwardly yes or no. It might be probability, or it might some arbitrary decision/activity metric for use elsewhere.

A state and decision/activity are presented to the case base. The case base is searched for the closest match (if there is one) and the outcome of that match is given.

A NetLogo case consists of a state in any of the standard Netlogo variables, such as list, number, string, etc. This is strictly defined by the `cbr:lambda` which is the comparator program used to determine the "distance" the three cases:

+ yes-case
+ no-case
+ reference-case

are relative to ecah other. That is, if: 

+ the yes-case is 'closer' to the reference-case than the no-case using `cbr:lamda`  to the reference-case then `cbr:lt` is returned
+ the no-case is 'closer' to the reference-case than the yes-case using `cbr:lamda`  to the reference-case then `cbr:gt` is returned
+ the no-case is 'same distance' to the reference-case than the yes-case using `cbr:lamda`  to the reference-case then `cbr:eq` is returned
+ the no-case is 'closer' using `cbr:lamda`  to the reference-case then `cbr:lt` is returned

The `cbr:lamda` is then used by `cbr:match` to determine a single closest match of a "hypothetical" case that is presented to `cbr:match` which consists of a state and decison and returning the 'best match' base on the repeated use of `cbr:lamda` to determine which is the closest match. If the match does not meet a suitabl criterion then 

If there are more than one match then the case with the highest rank is returned, or failing that the oldest.  If both these conditions are equal then the result is a random selection from among those cases.

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

This is the reporter that does distance comparison. This is never called directly

#### Parameters

+ case base object 

+ An anonymous reporter or named reporter, which must take four parameters 

    - case base object
    - yes-case
    - no-case
    - reference-case

#### Returns

This *must* return one of the following

+ `cbr:lt`
+ `cbr:gt`
+ `cbr:incmp`
+ `cbr:eq`

he current represantion that `cbr:lamda` must return if of the three cases

+ yes-case;
+ no-case, and
+ reference-case,

when the yes-case and no-case "are an equal distance" from the reference case.

### cbr:eq

This is used to standardise response from `cbr:lamda` so `cbr:match` will work correctly with it.

#### Parameters

The current represantion that `cbr:lamda` must return if of the three cases

+ yes-case;
+ no-case, and
+ reference-case,

when the yes-case is "equal"  distance from the reference case as the no-case.

### cbr:gt

This is used to standardise response from `cbr:lamda` so `cbr:match` will work correctly with it.

#### Parameters

None

#### Returns

The current represantion that `cbr:lamda` must return if of the three cases

+ yes-case;
+ no-case, and
+ reference-case,

when the yes-case is "further" from the reference case than the no-case.

### cbr:lt

This is used to standardise response from `cbr:lamda` so `cbr:match` will work correctly with it.

#### Parameters

None

#### Returns

The current represantion that `cbr:lamda` must return if of the three cases

+ yes-case;
+ no-case, and
+ reference-case,

when the yes-case is "closer" to the reference case than the no-case.

### cbr:incmp

#### Parameters

+ None

#### Returns

The current represantion that `cbr:lamda` must return if any of  the three cases

+ yes-case;
+ no-case, and
+ reference-case,

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

This could be done with tables. There is commented code showing how to set this up using tables as state.

## EXTENDING THE MODEL

(suggested things to add or change in the Code tab to make the model more complicated, detailed, accurate, etc.)

## NETLOGO FEATURES

(interesting or unusual features of NetLogo that the model uses, particularly in the Code tab; or where workarounds were needed for missing features)

## RELATED MODELS

(models in the NetLogo Models Library and elsewhere which are of related interest)

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
NetLogo 6.2.2
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

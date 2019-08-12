A case base is a series of cases. A case base is denoted `cb` below and cases
are denoted as `case`, `case_0`, `case_1` ...

Initially the case-base and particular case were identified using
string-identifiers. Upon discussion we have agreed to use the more natural
object referencing provided by the NetLogo plugin API.


# Case Base

## cbr:new

### Parameters

None

### Return

An instance identifying the case base.

### Purpose

Creates an empty case base.

## cbr:size

### Parameters 

+ `cb` (inferred instance) - An object identifying the case base.

+ `size` (double) - Number of cases. No cases can be added if this number is
  exceeded until cases are removed.

### Return

None.

### Purpose

Sets the size of the case base specified in the parameters.

If this number is set then `cbr:forget` must be false.


## cbr:case

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `decision` (anything) - The decision to match

+ `state` (table) - The state to match

 `outcome` (anything) - The outcome of this state.

### Return

+ `case` (Netlogo instance) - An object identifying the case

### Purpose 

This looks for the case based on the `decision` and `state` variables
and matching all the decisions in `cb` by using the code in cbr:case-lambda.

### Exception

+ Invalid case base
+ Invalid decision
+ Invalid state

## cbr:case-lambda

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ __[ReporterType]__ - Reporter lambda to determine which case to select.

### Return 

None.

### Purpose

Create the anonymous lambda used to determine how the `decision` and `state`
variables are used to select a case. 

Ideally it  would be nice if this could be a named procedure.

This will be invoked, when the case the case-base comparators, in particular
by cbr:comparator, which in turn is called by cbr:closer, cbr:comparable and
cbr:same. 

### Exception

+ Invalid case base
+ Invalid lambda or reporter

## cbr:remove

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - An object identifying the case

### Return

None.

### Purpose

Removes a particular case from a particular case base.

### Exception

+ Invalid case base
+ Invalid case

## cbr:forgettable

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `forget` (boolean) - True for the ability to forget cases in the case base,
  false otherwise.

### Return

None

### Purpose

A boolean indicating whether cases may be forgotten. If this is not set set
then the number of cases cannot exceed the value set in `cbr:size cb`.

### Exception

+ Invalid case base

## cbr:forget

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `time` (string) - Representing date time in some orderable format. 

### Return

None

### Purpose

This as part of the case structure a time will be included, and can be obtained
with `cbr:time cb case`. Any cases that have times that are older than that
supplied to this routine will be deleted from the case base `cb`.

This can only be used if the `cbr:forget cb` has been set to true. 

### Exception

+ Invalid case base
+ Invalid time

# Case

## cbr:add-case 

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `state` (table) - The state is what is the current state of the
  variables that make up a state 

+ `outcome` (table) - The outcome is what we are trying to match when making a
  case-selection. 

+ `decision` (anything) - The decision is what we are going to do.

+ `time` (string) - The time is when the case was added. Must be orderable.

### Return

+ `case` (inferred instance) - Some object identifying the particular case.

### Purpose

Adds a case to the case base, and time-stamps it.

The last four parameters are optional (note the way Netlogo works is
positional).

### Exception

+ Invalid case base
+ Invalid time

## cbr:time

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `time` (string) - The time is when the case was added. Must be orderable.

### Purpose

Gets the time when the case was added to the case-base.

### Exception

+ Invalid case base

## cbr:score

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `rank` (double) The score, used if we have matching (outcomes, state).

### Purpose

Gets the ranking of the particular case given that there can be more than one case with the same state.

### Exception

+ Invalid case base
+ Invalid case

## cbr:set-score

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

+ `case-score` (NetLogo Object) - Some score that the programmer to use determined precedence for  a case.

### Return

None.

### Purpose

Increments the ranking of the particular case given that there can be more than
one case with the same state.

### Exception

+ Invalid case base
+ Invalid case

## cbr:state

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `state` (anything) - The state is what is the current state of the
  variables that make up a state 

### Purpose

Get the state for a particular case in a case-base.

### Exception

+ Invalid case base
+ Invalid case

## cbr:decision

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `decision` (anything) - The decision is what we are going to do, if this case
+ is selected.

### Purpose

Get the decision for a particular case in a case-base.

### Exception

+ Invalid case base
+ Invalid case

## cbr:outcome

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `outcome` (table) - The outcome is what we are trying to match when making a
  case-selection. 

### Purpose

Get the outcome for a particular case in a case-base.

### Exception

+ Invalid case base
+ Invalid case

## cbr:closer

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case_0` (NetLogo instance) - some NetLogo instance identifying a case

+ `case_1` (NetLogo instance) - some Netlogo instance identifying a comparison case.

+ `case_2` (NetLogo instance) - some Netlogo instance identifying a  relative case.

### Return

Boolean

### Purpose

Compares if `case_0` is closer to `case_1`, than `case_0` is to `case_2`.
Thus it returns true if `cbr:yes` is returned from the call to cbr:comparator,
false if `cbr:no` is returned.

### Exception

+ Invalid case base
+ Invalid case

## cbr:same

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case_0` (NetLogo instance) - some NetLogo instance identifying a case

+ `case_1` (NetLogo instance) - some Netlogo instance identifying a comparison case.

+ `case_2` (NetLogo instance) - some Netlogo instance identifying a  relative case.

### Return

Boolean

### Purpose

Determines if `case_0` is same distance to  `case_1`, that `case_0` is to `case_2`.
Thus it returns true if `cbr:equal` is returned from the call to cbr:comparator.

### Exception

+ Invalid case base
+ Invalid case

## cbr:comparable

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case_0` (NetLogo instance) - some NetLogo instance identifying a case

+ `case_1` (NetLogo instance) - some Netlogo instance identifying a comparison case.

+ `case_2` (NetLogo instance) - some Netlogo instance identifying a  relative case.

### Return

Boolean

### Purpose

Compares if `case_0`, `case_1` and `case_2` can be compared. That is
`cbr:invalid` is not returned from `cbr:comparator` on the three values.

### Exception

+ Invalid case base
+ Invalid case

## cbr:comparator

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ __[ReporterType]__ - lamda to determine whether which of `case_1` and
  `case_2` is closer to `case_0`.

### Return

None.

### Purpose

Uses the anonymous lamba or the reporter used to determine how a case is evaluated
against other cases.

This is the lamda in use by cbr:comparable, cbr:same and cbr:closer.

It _must_ return one of the following (although, obviously this is up-to the
writer of the anonymous lamda, or reporter). This could be exceptioned.

+ `cbr:equal` - target-case is same distance from some-case and relative-case 
+ `cbr:yes` - target-case is closer to some-case 
+ `cbr:no` - target-case is closer to relative-case
+ `cbr:invalid` - one pair of target-case, some-case and relative-case is not
  comparable.

`cbr:equal`, `cbr:yes`, `cbr:no` and `cbr:invalid` are just simple routines
that take no arguments and just return a string. This is an attempt to force
the modeller to use the correct return from the comparator. The comparator
could be test instantiated just to make sure this is all that is being returned
(or simple syntax checking, by grepping to make sure that this is all the
modeller is returning. So any "report" in the code would have to followed by
one of these possibilities).

For example, we might have:

```

let decider [ [ target-case some-case relative-case ] -> 

if target-case - relative-case = target-case - some-case [ report cbr:equal]
if target-case - relative-case > target-case - some-case [ report cbr:yes ]
if target-case - relative-case < target-case - some-case [ report cbr:no ]
report cbr:invalid

]
```

### Exception

+ Invalid case base
+ Invalid anonymous lamda or reporter






# Introduction

This was the original specification for the extension. I need to review this
and see if there is anything useful we can keep.

# Commands

## cbr:new

### Parameters

None

### Return

An instance identifying the case base.

### Purpose

Creates an empty case base.

## cbr:add

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `decision` (anything) - The decision is what we are going to do.

+ `state` (table) - The state is what is the current state of the
  variables that make up a state

+ `outcome` (table) - The outcome is what actually happens.

### Return

+ `case` (inferred instance) - Some object identifying the particular case.

### Purpose

Adds a case to the case base, and time-stamps it. It uses the value of ticks to do this.

The last four parameters are optional (note the way Netlogo works is
positional).

### Exception

+ Invalid case

+ Invalid time

## cbr:combine

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `cb`  An object identifying a case base to append to this case base.

### Return

None

### Purpose

Appends a casebase to the invoking instance of a case base. Be careful using
this as it can cause a memory leak.

### Exception

+ Invalid case base

## cbr:all

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

### Return

+ [list] with multiple instances of `case` for that case base `cb`.
### Purpose

Returns all cases in a case base.

### Exception

+ Invalid case

## cbr:remove

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - An object identifying the case

### Return

None.

### Purpose

Removes a particular case from a particular case base.

### Exception

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

+ Invalid case

## cbr:forget

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

### Return

None

### Purpose

Tells a case base to  discard any cases older than `cbr:get-time` for that case.
base. Or it will remove the oldest cases that make the case base exceed
`cbr:get-max-size`.

### Exception

+ Invalid case

## cbr:get-earliest
## cbr:set-earliest
## cbr:get-max-size

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

### Return

+ `size` (double) - Number of cases. No cases can be added if this number is
  exceeded until cases are removed.

### Purpose

Sets the size of the case base specified in the parameters.

If this number is set then `cbr:forget` must be false.

### Exception

+ Invalid case

## cbr:set-max-size

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `size` (double) - Number of cases. No cases can be added if this number is
  exceeded until cases are removed.

### Return

None.

### Purpose

Sets the size of the case base specified in the parameters.

If this number is set then `cbr:forget` must be false.

### Exception

+ Invalid case base

## cbr:get-rank

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `rank` (double) The score, used if we have matching (outcomes, state).

### Purpose

Gets the ranking of the particular case given that there can be more than one case with the same state.

### Exception

+ Invalid case

## cbr:set-rank

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

+ Invalid case

## cbr:get-time

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

### Return

+ `time` (string) - The time is when the case was added. Must be orderable.
This is a NetLogo tick so is a positive integer or 0.

### Purpose

Gets the time when the case was added to the case-base.

### Exception

+ Invalid case base

## cbr:set-time

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `case` (inferred instance) - Some object identifying the particular case.

+ `time` (string) - The time is when the case was added. Must be orderable.
This is a Netlogo tick so must be an integer greater or equal to zero.

### Return

None.

### Purpose

Sets the time when the case was added to the case-base. This is a Netlogo tick
so must be an integer greater or equal to zero.

### Exception

+ Invalid case base

## cbr:resize

## cbr:lambda

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

+ Invalid case
+ Invalid lambda or reporter

+ `cb` (inferred instance) - An object identifying the case base.

+ Anonymous or named reporter that takes four arguments.

### Returns nothing

This takes a Netlogo anonymous or named NetLogo reporter.

### Purpose

The reporter takes for arguments:

+ `cb` (inferred instance) - An object identifying the case base.

+ `case_0` (NetLogo instance) - some NetLogo instance identifying a case

+ `case_1` (NetLogo instance) - some Netlogo instance identifying a comparison case.

+ `case_2` (NetLogo instance) - some Netlogo instance identifying a reference case.

This anonymous  reporter must return one of the following:

+ `cbr:equal` - if `case_0` and `case_1` are the same _distance_ from `case_2`
+ `cbr:yes` - if `case_0` is _closer_ to `case_2` than `case_1` is
+ `cbr:no` - if `case_1` is _closer_ to `case_2` than `case_0` is
+ `cbr:invalid` - if there is no way of determining the distance between any of
`case_0`, `case_1` and `case_2`.

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
### Returns

None.

### Exceptions

+ Invalid reporter
+ Invalid case base

## cbr:matches

## cbr:match

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `decision` (anything) - The decision is what we are going to do.

+ `state` (table) - The state is what is the current state of the
  variables that make up a state

### Returns

+ `case`

### Purpose

Get the closest match to the supplied `decision` and `state` in the case base `cb`

## Exceptions




































## cbr:case

### Parameters

+ `cb` (inferred instance) - An object identifying the case base.

+ `decision` (anything) - The decision to match

+ `state` (table) - The state to match

+ `outcome` (anything) - The outcome of this state.

### Return

+ `case` (Netlogo instance) - An object identifying the case

### Purpose

This looks for the case based on the `decision` and `state` variables
and matching all the decisions in `cb` by using the code in cbr:case-lambda.

### Exception

+ Invalid case
+ Invalid decision
+ Invalid state

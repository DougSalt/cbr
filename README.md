# PURPOSE

A NetLogo case based reasoning toolkit, `cbr` as a NetLogo plugin.

# INSTALLATION

See the [install document](INSTALL.md)

# BUILD

See the [build document](BUILD.md)

# MANIFEST

+ BUILD.md - the build instructions
+ INSTALL.md - how to install the plug-in in NetLogo
+ bin - directory where all the scripts and compiled classes are stored. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful scripts that are not automatically generated present in this
  directory.
+ doc - documentation directory. Contains documentation. Be
  careful some IDEs automatically clear this directory on a clean - there are
  useful files that are not automatically generated present in this directory.
+ INSTALL.md - instructions on how to install the plug-in.
+ jar-manifest.txt - manifest used to create the cbr.jar - the plugin.
+ lib - required libraries - these are already present in NetLogo.
+ LICENSE.txt - How you may use this plugin.
+ MANUAL.md
+ paper - A paper describing the plugin.
+ PUBLISHING.md - the instructions on how to release for use as a published
  NetLogo extension.
+ README.md - this file.
+ src - The source code for the plugin. Normally this would be something like
  `src\uk\ac\hutton\cbr` or whatever. The problem with this is that the plugin
  extension for NetLogo makes it difficult to adopt this structure, therefore
  is just bunged in `src` where all classes share the default package space.
+ test - A test NetLogo model. Normally we would used automated testing. The
  problem with this is setting up things like the Context is rather involved in
  NetLogo, so we use a NetLogo model to test the plug-in. There is a README.md
  in this directory with instructions on how to do this. Also the actual plugin
  jar `cbr.jar` is in a subdirectory `cbr`, which is the jar you need to
  install the plugin.
+ TODO - list of outstanding tasks to complete on the plugin.
+ tutorial - a presentation giving a brief explanation of how to use the
  NetLogo extension
+ UPGRADING.md - instructions on what to change if you should have to upgrade the version of NetLogo.


# INSTRUCTIONS

See also  [the manual](MANUAL.md)

<!-- Cut below this line and add into the Notes section of the demo Netlogo
model, test/cbr.nlogo -->

## WHAT IS IT?

This is an example of how to use the case based reasoning plugin.

## HOW IT WORKS

The code implements a reference version of code using the extension that is
described here.

Select the run-testing button to make sure that the code is behaving as it
should. If there is no error dialogue then the code is working as expected. The
code also shows how to the use the extension.

Normally a  case base consists of a series of cases, each of these cases
consist of

+ state
+ decision/activity
+ outcome

The state can be anything such as the bank balance of the agent. The decision/activity might be to install central heating. The outcome might be straight forwardly yes or no. It might be probability, or it might some arbitrary decision/activity metric for use elsewhere.

A state and decision/activity are presented to the case base. The case base is
searched for the closest match (if there is one) and the outcome of that match
is given.

A NetLogo case consists of a state in any of the standard Netlogo variables,
such as list, number, string, etc. This is strictly defined by the `cbr:lambda`
which is the comparator program used to determine the "distance" the three
cases:

+ case A
+ case B
+ referent case R

are relative to ecah other. That is, if: 

+ the case A is 'closer' to the referent case R than the case B using
  `cbr:lamda`  to the referent case R then `cbr:lt` is returned
+ the case B is 'closer' to the referent case R than the case A using
  `cbr:lamda`  to the referent case R then `cbr:gt` is returned
+ the case B is 'same distance' to the referent case R than the case A using
  `cbr:lamda`  to the referent case R then `cbr:eq` is returned
+ the case B is 'closer' using `cbr:lamda`  to the referent case R then
  `cbr:lt` is returned

The `cbr:lamda` is then used by `cbr:match` or `cbr:matches` to determine a
single closest match of a "hypothetical" case that is presented to `cbr:match`
or more or one equally close matches if `cbr:matches` is used. The case will
consists of a state and decison and returning the 'best match' base on the
repeated use of `cbr:lamda` to determine which is the closest match. If the
match does not meet a suitable criterion then `cbr:incmp` is returned.

When using `cbr:match`, then if there are more than one match then the case
with the highest rank is returned, or failing that the oldest.  If both these
conditions are equal then the result is a random selection from among those
cases. More than one case may be returned if `cbr:matches` is used.

If no `cbr:lamba` is provided then a default lambda is used. The specification
for this is as follows.

The comparison algorithm always has three arguments:

+ Case A
+ Case B
+ Referent case R

Cases A and B need not be in a case base; they may also have empty Decision and
Outcome parts, or simply be a State. Case R may have empty Decision and Outcome
parts, or simply be a State. (In a typical use-case, R is an ‘expected state of
the world’ for which the deciding agent is trying to find a range of cases.)

The algorithm returns one of four responses:

+ `cbr:gt` – iff A more similar to R than B
+ `cbr:lt` – iff B more similar to R than B
+ `cbr:eq` – iff A and B are equally similar to R
+ `cbr:incmp` – iff A and B are not comparably similar to R

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

Adds a case the the NetLogo case base object. The tick at which this takes
place is also recorded and can be accessed using `cbr:get-time`. This property
can be manipulated by using`cbr:set-time`

Note the case object cannot exist independently of the case base object. If the
latter is destroyed or removed, then so to is the former.

+ case base object 

+ state (in a form that `cbr:lamda` requires for doing the "distance" function.

+ decision/activity (in a form that `cbr:lamda` requires for doing the
  "distance" function.

+ outcome (in any form that you require, providing this is not used as part of
  the decison making in `cbr:lamda`).

#### Returns

Returns a case object. This is not really useful, but can be used for display
and debugging uses if needs be.

### cbr:combine

All the cases from the second case base are appended to the case base in the
first argument

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

A state in the form the `cbr:lamda` uses. In the example code showed above this
takes the form of a list of strings.

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

Time is used in conjunction `cbr:forget` to remove aged cases from the case
base. The time is 0 or a postive number and corresponds to the number of ticks.

#### Parameters

+ case base object 

+ case object (returned when the case is created)

+ number of ticks representing when the case was entered into the case base table.

#### Returns

Nothing.

### cbr:get-time

Obtains/gets the time of origin on a particular case within a case base object.
This is measured in number of ticks, is 0 or positive.  Time is used in
conjunction `cbr:forget` to remove aged cases from the case base.

#### Parameters

+ case base object 

+ case object within the above case base

#### Returns

A number of ticks, representing when the case was added to the case base. This
is generally the current cclick but may be manipulated using `cbr:set-time`.

### cbr:set-earliest

This sets the earliest tick. If a case base has a creation time (as a tick)
older than this, then upon the evocaction of the next `cbr:forget` then this
case will be deleted, i.e. *forgotten*.

#### Parameters

+ case base object 

+ number representing the oldest tick, before which if any case is stamped with
  an earlier time then these cases will be removed upon invocation of
  `cbr:forget`. The number in this case corresponds to the number of ticks.

#### Returns

Nothing.

### cbr:forget

If the `cbr:set-earliest` has been used, then `cbr:forget` will enforce this
limit. We have done it this way to prevent exceptions being thrown if the this
limit is exceeded, thus it requires explicit invocation via the user to make
the case base forget.

A tick is used to denote time. A NetLogo case base will have as part of its
metadata the time before which all cases acquired at that time will be
forgotten. This property may be obtained using the `cbr:get-earliest` and by
default is set to 0.

The actual age of a case within a casebase can be manipulated with the use of
`cbr:set-time` and `cbr:get-time`.

### cbr:lambda

This may the reporter that does distance comparison. This is never called
directly If a `cbr:lambda` is not provided, then the default comparator is
called. The default comparator has the following specification.

The algorithm should do the following:

1. If R is a case, then set R to the State part of the case

2. If A is a case, then set A to the State part of the case

3. If B is a case, then set B to the State part of the case

4. We now consider different datatypes for R, A and B in S = { number } and M =
   { string, patch, turtle, link, list, agent set } a N. B. S is a set of
   scalar datatypes; M is a set of multidimensional datatypes

5. If R, A and B are not the same datatype, return `cbr:incmp`

6. If R, A and B are all numbers, then if abs (R – A) &lt; abs(R – B) return
   `cbr:gt`; else if abs (R – A) &gt; abs(R – B) return `cbr:lt`; else return
   `cbr:eq`

7. If R, A and B are all agent sets, then let Am = A – (A intersect B) and let
   Bm = B – (A intersect B). If Am and Bm are both empty, return `cbr:eq`.  Let
   AmR = Am – (Am intersect R); Let BmR = Bm – (Bm intersect R); Let AR = A
   intersect R; Let BR = B intersect R. If AR is a superset of BR and AmR is a
   subset of BmR, return `cbr:gt`; Else if BR is a superset of AR and BmR is a
   subset of AmR, return `cbr:lt`; Else return `cbr:incmp` **Basic principle:**
   A is more similar to R than B is if all agents that B has in common with R,
   A also has in common with R, and all agents that A does not have in common
   with R, B also does not have in common with R. If there are members of R
   that are members of A but not B, and there are also members of R that are
   members of B but not A, then A and B are incomparably similar to R;
   likewise, if we look at members of A and B that are not members of R, unless
   one is a subset-of-or-equal-to the other, A and B are incomparably similar.
   The diagram below shows the three conditions in which the agent set
   comparisons are not incomparable. Note that in the left one, if B – (A
   intersect B) is empty, A is still more similar than B to R; also, if A – (A
   intersect B) is empty, A is still more similar to R. If both those regions
   are empty, then A and B are equal. The corresponding points are true of the
   middle diagram (i.e. with A and B swapped).  ![Intersection diagrams for the
   default comparator](../doc/default-comparator.png)

8. If A, B and R are all lists, then let answer = `cbr:eq`. For i in
   1:min(length of A, length of B, length of R): if A[i] == R[i] and B[i] !=
   R[i], then if answer == `cbr:lt`, return `cbr:incmp`; else let answer =
   `cbr:gt`. Else if B[i] == R[i] and A[i] != R[i], then if answer == `cbr:gt`,
   return `cbr:incmp`; else let answer = `cbr:lt`. End For. If answer ==
   `cbr:gt` and A has the shortest length and and For all j in (i +
   1):min(length of B, length of R), B[j] != R[j], then return `cbr:gt`. If
   answer == `cbr:lt` and B has the shortest length and For all j in (i +
   1):min(length of A, length of R), A[j] != R[j], then return `cbr:lt`. If A
   and B have different lengths, return `cbr:incmp`. If For all j in (i +
   1):(length of A), A[j] == B[j], then return `cbr:eq`ual else return
   `cbr:incmp` **Basic principle:** A is more similar-to R than B if everywhere
   that B is equal to R, A is also equal to R

9. If A, B and R all being strings can work on the same basis as lists, with i
   and j indexing characters.

10. If A, B and R are all patches then they can also be handled similarly to
    lists – each of the default and declared patches-own variables would be put
    into the lists in the same order.

This is the comparator used in the test program to which this documentation is attached.

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

when the case A and the case B "are an equal distance" from the referent case
R.

### cbr:eq

This is used to standardise response from `cbr:lamda` so `cbr:match` and
`cbr:matches` will work correctly with it.

#### Parameters

The current represantion that `cbr:lamda` must return if of the three cases

+ case A;
+ case B, and
+ referent case R,

when the case A is "equal"  distance from the referent case R as the case B.

### cbr:gt

This is used to standardise response from `cbr:lamda` so `cbr:match` and
`cbr:matches` will work correctly with it.

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

The current represantion that `cbr:lamda` must return if any of  the three
cases

+ case A;
+ case B, and
+ referent case R,

cannot be used for comparison.

### cbr:get-earliest

This gets the earliest time at which a case in a casebase will have been
created at. Before this tick if `cbr:forget` is called then all cases with the
casebase that have a time stamp (when they were notionally create) less than
this value, then these cases will be deleted from the case base.

#### Parameters

+ case base object 

#### Returns

+ A number which is the maxiumum number of cases the case base can contain.  

### cbr:set-earliest

This gets the earliest time at which a case in a casebase will have been
created at. Before this tick if `cbr:forget` is called then all cases with the
casebase that have a time stamp (when they were notionally create) less than
this value, then these cases will be deleted from the case base.

#### Parameters

+ case base object 

+ A number representing a tick. 

#### Returns

Nothing.

### cbr:set-max-size

This gets the maxium size for the case base. That is the maxiumum number of
cases it may contain. The case base may actually contain more cases than this
number and it requires the specific invocation of  `cbr:resize` to enforce this
size restriction. Upon the invocation of `cbr:resize` those case with the
lowest rank will be deleted. If there are two cases with equal rank then the
oldest will then be deleted.

#### Parameters

+ case base object 

+ A number which is the maxiumum number of cases the case base can contain.  

#### Returns

Nothing.

### cbr:get-max-size

This sets the maxium size for the case base. That is the maxiumum number of
cases it may contain. The case base may actually contain more cases than this
number and it requires the specific invocation of  `cbr:resize` to enforce this
size restriction. Upon the invocation of `cbr:resize` those case with the
lowest rank will be deleted. If there are two cases with equal rank then the
oldest will then be deleted.

#### Parameters

+ case base object 

+ A number which is the maxiumum number of cases the case base can contain.  

#### Returns

Nothing.

### cbr:set-rank

This sets the rank on a particular case in a case base. When choosing between
two cases when decreasing the size of a case base to a specified maximum number
of cases, then to determine which cases should be deleted to reduce the size of
the case base, we use the concept of the rank. All cases start with 0 rank.
However, this rank can be set using this primitive, and any case with a higher
rank will be preferred when retaining cases.

#### Parameters

+ case base object 

+ a number representing the rank of the case. The higher the rank, the more
  likely it is that the case will survive.

#### Returns

Nothing.

### cbr:get-rank

This gets the rank on a particular case in a case base. When choosing between
two cases when decreasing the size of a case base to a specified maximum number
of cases, then to determine which cases should be deleted to reduce the size of
the case base, we use the concept of the rank. All cases start with 0 rank.
However, this rank can be set using this primitive, and any case with a higher
rank will be preferred when retaining cases.

#### Parameters

+ case base object 

#### Returns

Nothing.

### cbr:resize

This resizes the case base to the number of cases specified by
`cbr:set-max-size`. When choosing between two cases when decreasing the size of
a case base to a specified maximum number of cases, then to determine which
cases should be deleted to reduce the size of the case base, we use the concept
of the rank. All cases start with 0 rank. However, this rank can be set using
this primitive, and any case with a higher rank will be preferred when
retaining cases.

This command is explicit. It has been done this way for two reasons. Firstly
this gives control to the user and secondly the only way to interrupt the work
flow in NetLogo appears to be some kind of exception. This, of course means
that the number of cases in a case base can exceed this number. Again this is
intentional and the number can be checked using the code `length cbr:all
some-case-base`.

#### Parameters

+ case base object 

#### Returns

Nothing.

### cbr:match

This returns the closet matched case that matches the state in the state
parameter and the decision/activity that has been selected.

#### Parameters

+ case base object 

+ state in a format that can be use by `cbr:lambda` to do the distance
  assessment between cases, which describes the necessary state of the agent)

+ decision/activity in a format that can be use by `cbr:lambda` to do the
  distance assessment between cases, which describes the decision or activity
  that is the agent is about to make or perform).

#### Returns

Nobody if there is no match otherwise returns a match case from the case base,
which then may be interrogated using

+ `cbr:state`
+ `cbr:decision`
+ `cbr:outcome`

The first two can be used to validate the case the last is usually what we are
after in terms of makeing a decision.

This will return only one case based on the selection criteria mentioned in the
"HOW IT WORKS" section above.

### cbr:matches

This returns the set of closest matched cases that match the state in the state
parameter and the decision/activity that has been selected.

#### Parameters

+ case base object 

+ state in a format that can be use by `cbr:lambda` to do the distance
  assessment between cases, which describes the necessary state of the agent)

+ decision/activity in a format that can be use by `cbr:lambda` to do the
  distance assessment between cases, which describes the decision or activity
  that is the agent is about to make or perform).

#### Returns

Nobody if there is no match otherwise returns a list of cases from the case
base, which then may be interrogated using

+ `cbr:state`
+ `cbr:decision`
+ `cbr:outcome`

The first two can be used to validate the case the last is usually what we are
after in terms of makeing a decision.

## THINGS TO NOTICE

(suggested things for the user to notice while running the model)

## THINGS TO TRY

The typical workflow is 

1. instantiate a case base using `cbr:new`
2. add some cases to it using `cbr:add`
3. query the case base using either `cbr:match` or `cbr:matchess` to return one
   or more cases
4. Interrogate the returned case outcome to see what the case base recommends.

In addition to this you might to add the resultant state, decision and outcome
back into the case base in order to emulate memory.


## EXTENDING THE MODEL

As mentioned above you can write your own default comparator. Here is an
example of how to do this.

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

This example makes sure the decision is the same in all the matched cases as
well. The defautl comparator does not do this.

Once this is implemented then it can loaded against a particular case base by
using the command:

```
cbr:lambda new-comparator  

```
## NETLOGO FEATURES

(interesting or unusual features of NetLogo that the model uses, particularly
in the Code tab; or where workarounds were needed for missing features)

## RELATED MODELS

achsium.nlogo uses this case base reasoner.

## CREDITS AND REFERENCES

Author doug.salt@hutton.ac.uk (see https://gitlab.com/doug.salt/cbr for the repository).
@

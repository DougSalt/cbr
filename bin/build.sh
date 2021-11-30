#!/bin/sh
cd bin
JAR=jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAR='/cygdrive/c/Java/jdk-17.0.1/bin/jar'
fi

"$JAR" cvmf ../jar-manifest.txt ../test/cbr/cbr.jar \
	AddCase.class \
	AllCases.class \
	Case.class \
	CaseBase.class \
	CaseBaseException.class \
	CaseLambda.class \
	CBRExtension.class \
	CombineCaseBases.class \
	Decision.class \
	DefaultComparator.class \
	Equal.class \
	EqualBoolean.class \
	Forget.class \
	GetEarliest.class \
	GetMaxSize.class \
	GetRank.class \
	GetTime.class \
	GreaterThan.class \
	GreaterThanBoolean.class \
	Incomparable.class \
	IncomparableBoolean.class \
	LessThan.class \
	LessThanBoolean.class \
	Match.class \
    Matches.class \
	NetLogoCase.class \
	NetLogoCaseBase.class \
	New.class \
	Outcome.class \
	Remove.class \
	Resize.class \
	SetEarliest.class \
	SetMaxSize.class \
	SetRank.class \
	SetTime.class \
	State.class

cp ../test/cbr/cbr.jar ~/git/smartees/Fuel\ Poverty/Aberdeen/cbr

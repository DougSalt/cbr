#!/bin/sh
cd bin
JAR=jar
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAR='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/jar'
fi
"$JAR" cvmf ../jar-manifest.txt ../model/cbr/cbr.jar \
	AddCase.class \
	AddCaseBase.class \
	AllCases.class \
	Case.class \
	CaseBase.class \
	CaseBaseException.class \
	CaseLambda.class \
	CBRExtension.class \
	Closer.class \
	Comparable.class \
	ComparableFeature.class \
	ComparableFeatureValue\$1.class \
	ComparableFeatureValue\$Op.class \
	ComparableFeatureValue.class \
	Decision.class \
	Equal.class \
	Feature\$1.class \
	Feature\$Value.class \
	Feature\$WildCard.class \
	Feature.class \
	FeatureSet\$Value.class \
	FeatureSet.class \
	Forget.class \
	GetEarliest.class \
	GetMaxSize.class \
	GetRank.class \
	GetTime.class \
	Invalid.class \
	Match.class \
	NetLogoCase.class \
	NetLogoCaseBase.class \
	NetLogoFeature.class \
	NetLogoFeatureSet.class \
	NetLogoFeatureValue.class \
	NetLogoFeatureValueSet.class \
	New.class \
	No.class \
	Outcome.class \
	POCmp.class \
	Remove.class \
	Resize.class \
	Same.class \
	SetEarliest.class \
	SetMaxSize.class \
	SetRank.class \
	SetTime.class \
	State.class \
	StringFeature.class \
	StringFeatureValue.class \
	StringInitializer.class \
	Yes.class

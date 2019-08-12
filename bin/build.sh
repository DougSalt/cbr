#!/bin/sh
cd bin
if [ $(uname) = "CYGWIN_NT-10.0" ]
then
    JAR='/cygdrive/c/Program Files/Java/jdk1.8.0_77/bin/jar'
fi
"$JAR" cvmf ../jar-manifest.txt ../model/cbr/cbr.jar \
	AddCase.class \
	AddCaseBase.class \
	AllCases.class \
	CBRExtension.class \
	Case.class \
	CaseBase.class \
	CaseBaseException.class \
	CaseLambda.class \
	Closer.class \
	Comparable.class \
	ComparableFeature.class \
	ComparableFeatureValue\$1.class \
	ComparableFeatureValue\$Op.class \
	ComparableFeatureValue.class \
	DateUtils.class \
	Decision.class \
	Equal.class \
	Feature\$1.class \
	Feature\$Value.class \
	Feature\$WildCard.class \
	Feature.class \
	FeatureSet\$Value.class \
	FeatureSet.class \
	Forget.class \
	Forgettable.class \
	FromList.class \
	Invalid.class \
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
	Rank.class \
	Remove.class \
	Resize.class \
	SetRank.class \
	SetTime.class \
	Same.class \
	MaxSize.class \
	State.class \
	StringFeature.class \
	StringFeatureValue.class \
	StringInitializer.class \
	Time.class \
	Yes.class

     D DSP             S             50
      *
     D  AAA015         S             15
      *
      *
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
      *
      * Examples for IFXX
      *
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFEQ      1
     C     1             OREQ      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFEQ      1
     C     2             ANDEQ     2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     2             IFEQ      1
     C     1             OREQ      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFEQ      1
     C     1             ANDEQ     2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFNE      2
     C     1             ANDNE     3
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFNE      2
     C     1             ANDEQ     1
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFEQ      1
     C     1             ANDNE     2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFEQ      1
     C     2             ANDNE     2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFEQ      2
     C     2             ORNE      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFEQ      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSEIF    2=1
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFGT      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA ERRATA') COMP(EQ)
  019C     1             IFGE      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFLE      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
    MU* VAL1(DSP) VAL2('PROVA') COMP(EQ)
  019C     1             IFLT      2
  019C                   EVAL      AAA015='PROVA'                               COSTANTE
     C                   ELSE
  019C                   EVAL      AAA015='PROVA ERRATA'                        COSTANTE
     C                   ENDIF
     C                   EVAL      DSP=AAA015
     C     DSP           DSPLY     
     C                   SETON                                        LR
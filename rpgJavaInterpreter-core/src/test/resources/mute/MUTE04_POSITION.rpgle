     DFIELD1           S             15A   INZ('AAAA')
     DCOUNT            S              8  0
     D NBR             S              8  0 INZ(10)
     D RESULT          S              8  0 INZ(0)
     DA                S              8  0 INZ(0)
     DB                S              8  0 INZ(1)
      *
     C                   CLEAR                   FIELD1
     C                   DO        COUNT
      * Test: annotation attached to line 12
    MU* VAL1(COUNT) VAL2(0) COMP(EQ)
     C                   EVAL      COUNT = COUNT + 1
     C                   IF        COUNT > 10
      * Test: annotation attached to line 16
    MU* VAL1(COUNT) VAL2(0) COMP(EQ)
     C                   EVAL      A = 1
     C                   ENDIF
     C                   ENDDO
     C                   SETON                                        LR
      *--------------------------------------------------------------*




















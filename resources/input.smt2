(declare-fun symvar865969300 () Int)
(declare-fun symvar420371703 () Int)
(assert ( and ( and ( and ( and true ( not ( > symvar420371703 50))) ( not ( and ( > ( * symvar420371703 2) 100) ( < ( * symvar420371703 2) 180)))) ( and ( > ( * symvar420371703 2) 60) ( < ( * symvar420371703 2) 70))) ( and ( < ( + symvar420371703 15) 50) ( > ( + symvar420371703 15) 44))))
(check-sat)
(get-model)
(exit)

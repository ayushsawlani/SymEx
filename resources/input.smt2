(declare-fun symvar1938972946 () Int)
(declare-fun symvar127616865 () Int)
(declare-fun symvar884363788 () Int)
(assert ( and ( and ( and ( and ( and ( and ( and ( and ( and ( and ( and true ( > symvar1938972946 50)) ( not ( and ( > ( + symvar1938972946 2) 100) ( < ( + symvar1938972946 2) 180)))) ( not ( and ( > ( + symvar1938972946 2) 60) ( < ( + symvar1938972946 2) 70)))) ( > ( + symvar1938972946 2) 65)) ( not ( and ( < ( + symvar1938972946 1) 50) ( > ( + symvar1938972946 1) 44)))) ( > symvar884363788 50)) ( and ( > ( + symvar884363788 2) 100) ( < ( + symvar884363788 2) 180))) ( not ( and ( < ( + symvar884363788 2) 50) ( > ( + symvar884363788 2) 44)))) ( > symvar127616865 50)) ( not ( and ( > ( + symvar127616865 2) 100) ( < ( + symvar127616865 2) 180)))) ( not ( and ( > ( + symvar127616865 2) 60) ( < ( + symvar127616865 2) 70)))))
(check-sat)
(get-model)
(exit)

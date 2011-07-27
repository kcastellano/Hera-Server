;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Hera Test for Sonogram Analysis and Pattern Recognition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Templates

(deftemplate medida
    (slot idMedida)
    (slot nombre)
    (slot valor)
    (slot tipo))

(deftemplate estudio
    (slot idEstudio)
    (slot trimestre)
)

(deftemplate diagnostico
    (slot idDiagnostico)
    (slot idMedida)
    (slot medida)
    (slot percentil)
    (slot edadGestacional)
    (slot resultado)
 )



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rules

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Saco Gestacional


(defrule regla-1
    "Embarazo completamente sano (Forma regular), calculado con diametro medio saco gestacional"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    =>
    (bind ?answer (saco-gestacional (integer ?t)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "el embarazo es completamente sano")))
    )

(defrule regla-2
    "Embarazo completamente sano (Forma irregular), calculado con diametro medio saco gestacional"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    =>
    (bind ?answer (saco-gestacional (integer ?t)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "el embarazo es completamente sano")))
    )

(defrule regla-3
    "Probabilidades de embarazo ectopico (Forma regular)"
    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida  (nombre ubicacion) (tipo saco-gestacional) (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m) (edadGestacional 0) (resultado "Hay probabilidades que el embarazo sea ectopico (Forma regular)")))
    )

(defrule regla-4
    "Probabilidades de embarazo ectopico (Forma irregular)"
    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m) (edadGestacional 0) (resultado "Hay probabilidades que el embarazo sea ectopico (Forma irregular)")))
    )

(defrule regla-5
    "Los embarazo con bordes irregulares por polipos, mielomas o DIU (Forma regular)"
    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Irregular))
    =>
    (assert (diagnostico   (idMedida ?m)(edadGestacional 0) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma regular)")))
    )

(defrule regla-6
    "Los embarazo con bordes irregulares por polipos, mielomas o DIU (Forma irregular)"
    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Irregular))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma irregular)")))
    )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vesicula Vitelina


(defrule regla-7
    "La gestacion puede ser interrumpida por diametro de 13 mm sin vesicula vitelina"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (test (= (integer ?t) 13))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor No))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Hay probabilidades de tener una gestacion interrumpida (Saco Gestacional sin V.V)")))
    )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Embrion

(defrule regla-8
    "La gestacion puede ser interrumpida por diametro de 20 mm sin embrion"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (test (= (integer ?t) 20))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor No))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Hay probabilidades de tener una gestacion interrumpida(Saco Gestacional sin embrion)")))
    )

(defrule regla-9
    "La gestacion puede ser interrumpida por CRL de 5 mm sin actividad cardiaca"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor Si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor No))
    (medida (nombre lcr) (valor ?l) (tipo embrion))
    (test (= (integer ?l) 5))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Hay probabilidades de tener una gestacion interrumpida (Embrion sin Actividad Cardiaca)")))
    )

(defrule regla-10
    "Hay probabilidades de que el bebe tenga cromosomopatias(traslucencia nucal)"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor Si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor Si))
    (medida (nombre traslucencia-nucal) (valor ?d) (tipo embrion))
    (test (> (float ?d) 3.5))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Traslucencia nucal)")))
    )

(defrule regla-11
    "Hay probabilidades de que el bebe tenga cromosomopatias(ductus venoso)"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor Si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor Si))
    (medida (nombre traslucencia-nucal) (valor ?d) (tipo embrion))
    (test (< (float ?d) 3.5))
    (medida (nombre ductus-venoso) (valor Reverso) (tipo embrion))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Ductus venoso)")))
    )

(defrule regla-12
    "Hay probabilidades de que el bebe tenga cromosomopatias(angulo facial)"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor Si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor Si))
    (medida (nombre traslucencia-nucal) (valor ?t) (tipo embrion))
    (test (< (float ?t) 3.5))
    (medida (nombre ductus-venoso) (valor Normal) (tipo embrion))
    (medida (nombre angulo-facial) (valor ?a) (tipo embrion))
    (test (> (integer ?a) 85))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Angulo facial)")))
    )


(defrule regla-13
    "Hay probabilidades de que el bebe tenga cromosomopatias(hueso nasal)"
    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor Si))
    (medida (nombre visible) (tipo embrion) (valor Si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor Si))
    (medida (nombre traslucencia-nucal) (valor ?d) (tipo embrion))
    (test (< (float ?t) 3.5))
    (medida (nombre ductus-venoso) (valor normal) (tipo embrion))
    (medida (nombre angulo-facial) (valor ?a) (tipo embrion))
    (test (< (integer ?a) 85))
    (medida (nombre hueso-nasal) (valor ausente) (tipo embrion))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Hueso nasal)")))
    )


(defrule r-tricuspidea

    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor si))
    (medida (nombre visible) (tipo embrion) (valor si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor si))
    (medida (nombre traslucencia-nucal) (valor ?d) (tipo embrion))
    (test (< (float ?t) 3.5))
    (medida (nombre ductus-venoso) (valor normal) (tipo embrion))
    (medida (nombre angulo-facial) (valor ?a) (tipo embrion))
    (test (< (integer ?a) 85))
    (medida (nombre hueso-nasal) (valor ausente) (tipo embrion))
    (medida (nombre r-tricuspidea) (valor anormal) (tipo embrion))
    =>
    (assert (diagnostico (idMedida ?m) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (R. Tricuspidea)")))
    )



(defrule embarazo-sano-crl

    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (tipo vesicula-vitelina) (valor si))
    (medida (nombre visible) (tipo embrion) (valor si))
    (medida (nombre actividad-cardiaca) (tipo embrion) (valor si))
    (medida (nombre traslucencia-nucal) (valor ?t) (tipo embrion))
    (test (< (float ?t) 3.5))
    (medida (nombre ductus-venoso) (valor normal) (tipo embrion))
    (medida (nombre angulo-facial) (valor ?a) (tipo embrion))
    (test (< (integer ?a) 85))
    (medida (nombre hueso-nasal) (valor presente) (tipo embrion))
    (medida (nombre r-tricuspidea) (valor normal) (tipo embrion))
    (medida (nombre crl) (valor ?l) (tipo embrion))
    =>
    (bind ?answer (crl-hadlock (integer ?l)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "El embarazo es completamente sano (Longitud craneo-caudal)")))
    )



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Query

(defquery diagnostico
  (declare (variables ?medida))
  (diagnostico (idMedida ?medida) (resultado ?resultado) (edadGestacional ?edad)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function

(deffunction saco-gestacional (?crl)
     ( bind ?answer (/ (+ 30 ?crl) 7) )
    (return ?answer)
    )
(deffunction acos (?arg)
     (call java.lang.Math acos ?arg))

(deffunction exp1 (?arg)
    (call java.lang.Math exp ?arg))

(deffunction pow (?arg ?arg2)
    (call java.lang.Math pow ?arg ?arg2))

(deffunction crl-hadlock (?crl)
     ( bind ?answer (exp(- (+ (-(+ 1.684969 (* .315646 (/ ?crl 10))) (* .049306 (pow (/ ?crl 10) 2))) (* .004057 (pow (/ ?crl 10) 3))) (* .000120456 (pow (/ ?crl 10) 4)) )))
    (return ?answer)
    )





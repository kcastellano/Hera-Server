;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Hera Test for Sonogram Analysis and Pattern Recognition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Templates

(deftemplate medida
    (slot idMedida)
    (slot nombre)
    (slot valor))

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

(deftemplate incertidumbre
    (slot valor)
)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rules

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Saco Gestacional


(defrule regla-1
    "Embarazo completamente sano (Forma regular), calculado con diametro medio saco gestacional"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion)  (valor Intrauterina))
    (medida (nombre bordes)  (valor Regular))
    (medida (nombre diametro) (valor ?t))
    =>
    (bind ?answer (saco-gestacional (integer ?t)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "el embarazo es completamente sano")))
    )

(defrule regla-2
    "Embarazo completamente sano (Forma irregular), calculado con diametro medio saco gestacional"
    (medida (idMedida ?m)(nombre forma) (valor Irregular))
    (medida (nombre ubicacion)  (valor Intrauterina))
    (medida (nombre bordes)  (valor Regular))
    (medida (nombre diametro) (valor ?t))
    =>
    (bind ?answer (saco-gestacional (integer ?t)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "el embarazo es completamente sano")))
    )

(defrule regla-3
    "Probabilidades de embarazo ectopico (Forma regular)"
    (medida (idMedida ?m) (nombre forma)  (valor Regular))
    (medida  (nombre ubicacion) (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m) (edadGestacional 0) (resultado "Hay probabilidades que el embarazo sea ectopico (Forma regular)")))
    )

(defrule regla-4
    "Probabilidades de embarazo ectopico (Forma irregular)"
    (medida (idMedida ?m) (nombre forma)  (valor Irregular))
    (medida (nombre ubicacion)  (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m) (edadGestacional 0) (resultado "Hay probabilidades que el embarazo sea ectopico (Forma irregular)")))
    )

(defrule regla-5
    "Los embarazo con bordes irregulares por polipos, mielomas o DIU (Forma regular)"
    (medida (idMedida ?m) (nombre forma)  (valor Regular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Irregular))
    =>
    (assert (diagnostico   (idMedida ?m)(edadGestacional 0) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma regular)")))
    )

(defrule regla-6
    "Los embarazo con bordes irregulares por polipos, mielomas o DIU (Forma irregular)"
    (medida (idMedida ?m) (nombre forma) (valor Irregular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Irregular))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma irregular)")))
    )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vesicula Vitelina


(defrule regla-7
    "La gestacion puede ser interrumpida por diametro de 13 mm sin vesicula vitelina"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (test (= (integer ?t) 13))
    (medida (nombre presencia) (valor No))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0)  (resultado "Hay probabilidades de tener una gestacion interrumpida (Saco Gestacional sin V.V)")))
    )

(defrule regla-8
    "La gestacion puede ser interrumpida por diametro de 13 mm sin vesicula vitelina"
    (medida (idMedida ?m)(nombre forma) (valor Irregular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (test (= (integer ?t) 13))
    (medida (nombre presencia) (valor No))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0)  (resultado "Hay probabilidades de tener una gestacion interrumpida (Saco Gestacional sin V.V)")))
    )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Embrion

(defrule regla-9
    "La gestacion puede ser interrumpida por diametro de 20 mm sin embrion"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (test (= (integer ?t) 20))
    (medida (nombre presencia) (valor Si))
    (medida (nombre visible) (valor No))
    =>
    (assert (diagnostico (idMedida ?m)(edadGestacional 0)  (resultado "Hay probabilidades de tener una gestacion interrumpida(Saco Gestacional sin embrion)")))
    )

(defrule regla-10
    "La gestacion puede ser interrumpida por CRL de 5 mm sin actividad cardiaca"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia) (valor Si))
    (medida (nombre visible) (valor Si))
    (medida (nombre actividadCardiaca)(valor No))
    (medida (nombre crl) (valor ?c))
    (test (= (integer ?c) 5))

    =>
    (assert (diagnostico (idMedida ?m)  (edadGestacional 0) (resultado "Hay probabilidades de tener una gestacion interrumpida (Embrion sin Actividad Cardiaca)")))
    )

(defrule regla-11
    "Hay probabilidades de que el bebe tenga cromosomopatias(traslucencia nucal)"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion) (valor Intrauterina))
    (medida (nombre bordes)(valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (valor Si))
    (medida (nombre visible) (valor Si))
    (medida (nombre actividadCardiaca) (valor Si))
    (medida (nombre traslucenciaNucal) (valor ?d))
    (test (> (integer ?d) 3.5))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Traslucencia nucal)")))
    )

(defrule regla-12
    "Hay probabilidades de que el bebe tenga cromosomopatias(ductus venoso)"
    (medida (idMedida ?m)(nombre forma)(valor Regular))
    (medida (nombre ubicacion)(valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    (medida (nombre presencia) (valor Si))
    (medida (nombre visible) (valor Si))
    (medida (nombre actividadCardiaca) (valor Si))
    (medida (nombre traslucenciaNucal) (valor ?d))
    (test (< (integer ?d) 3.5))
    (medida (nombre ductusVenoso) (valor Reverso))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0)  (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Ductus venoso)")))
    )

(defrule regla-13
    "Hay probabilidades de que el bebe tenga cromosomopatias(angulo facial)"
    (medida (idMedida ?m)(nombre forma) (valor Regular))
    (medida (nombre ubicacion)(valor Intrauterina))
    (medida (nombre bordes) (valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia) (valor Si))
    (medida (nombre visible) (valor Si))
    (medida (nombre actividadCardiaca)(valor Si))
    (medida (nombre traslucenciaNucal) (valor ?t))
    (test (< (integer ?t) 3.5))
    (medida (nombre ductusVenoso) (valor Normal))
    (medida (nombre anguloFacial) (valor ?a))
    (test (> (integer ?a) 85))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0)  (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Angulo facial)")))
    )


(defrule regla-14
    "Hay probabilidades de que el bebe tenga cromosomopatias(hueso nasal)"
    (medida (idMedida ?m)(nombre forma)(valor Regular))
    (medida (nombre ubicacion)(valor Intrauterina))
    (medida (nombre bordes)(valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia)(valor Si))
    (medida (nombre visible) (valor Si))
    (medida (nombre actividadCardiaca)(valor Si))
    (medida (nombre traslucenciaNucal) (valor ?t))
    (test (< (float ?t) 3.5))
    (medida (nombre ductusVenoso) (valor Normal))
    (medida (nombre anguloFacial) (valor ?a))
    (test (< (integer ?a) 85))
    (medida (nombre huesoNasal) (valor Ausente))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (Hueso nasal)")))
    )


(defrule regla-15
    "Hay probabilidades de que el bebe tenga cromosomopatias(r.tricuspidea)"
    (medida (idMedida ?m)(nombre forma)(valor Regular))
    (medida (nombre ubicacion)(valor Intrauterina))
    (medida (nombre bordes)(valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia)(valor Si))
    (medida (nombre visible)(valor Si))
    (medida (nombre actividadCardiaca)(valor Si))
    (medida (nombre traslucenciaNucal) (valor ?t))
    (test (< (float ?t) 3.5))
    (medida (nombre ductusVenoso) (valor Normal))
    (medida (nombre anguloFacial) (valor ?a))
    (test (< (integer ?a) 85))
    (medida (nombre huesoNasal) (valor Presente))
    (medida (nombre tricuspidea) (valor Anormal))
    =>
    (assert (diagnostico (idMedida ?m) (edadGestacional 0) (resultado "Se recomienda hacer un examen para detectar cromosomopatias (R. Tricuspidea)")))
    )



(defrule regla-16
     "Embarazo completamente sano (Forma regular), calculado con longitud craneo rabadilla"
    (medida (idMedida ?m)(nombre forma)(valor Regular))
    (medida (nombre ubicacion)(valor Intrauterina))
    (medida (nombre bordes)(valor Regular))
    (medida (nombre diametro) (valor ?d))
    (medida (nombre presencia)(valor Si))
    (medida (nombre visible)(valor Si))
    (medida (nombre actividadCardiaca) (valor Si))
    (medida (nombre traslucenciaNucal) (valor ?t))
    (test (< (integer ?t) 3.5))
    (medida (nombre ductusVenoso) (valor Normal))
    (medida (nombre anguloFacial) (valor ?a))
    (test (< (integer ?a) 85))
    (medida (nombre huesoNasal) (valor Presente))
    (medida (nombre tricuspidea) (valor Normal))
    (medida (nombre crl) (valor ?l))
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





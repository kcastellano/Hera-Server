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
    (slot edadGestacional)
    (slot resultado)
 )

(deftemplate proxima-medida
    (slot value))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rules



(defrule embarazo-sano

    (medida (idMedida ?m)(nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Regular))
    (medida (nombre diametro) (valor ?t))
    =>
    (bind ?answer (calcular-edad-gestacional (integer ?t)))
    (assert (diagnostico (idMedida ?m) (medida (integer ?t)) (edadGestacional ?answer) (resultado "el embarazo es completamente sano")))
    )

(defrule embarazo-ectopico-forma-regular

    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida  (nombre ubicacion) (tipo saco-gestacional) (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m)(resultado "Hay probabilidades que el embarazo sea ectopico (Forma regular)")))
    )

(defrule embarazo-ectopico-forma-irregular

    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Extrauterina))
    =>
    (assert (diagnostico  (idMedida ?m)(resultado "Hay probabilidades que el embarazo sea ectopico (Forma irregular)")))
    )

(defrule embarazo-forma-regular-bordes-regulares

    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Regular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Irregular))
    =>
    (assert (diagnostico   (idMedida ?m) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma regular)")))
    )

(defrule embarazo-forma-regular-bordes-irregulares

    (medida (idMedida ?m) (nombre forma) (tipo saco-gestacional) (valor Irregular))
    (medida (nombre ubicacion) (tipo saco-gestacional) (valor Intrauterina))
    (medida (nombre bordes) (tipo saco-gestacional) (valor Irregular))
    =>
    (assert (diagnostico   (idMedida ?m) (resultado "La paciente puede poseer mielomas, polipos o DIU (Forma irregular)")))
    )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Query

(defquery diagnostico
  (declare (variables ?medida))
  (diagnostico (idMedida ?medida) (resultado ?resultado) (edadGestacional ?edad)))

(defquery obtener-edad-gestacional
    (declare (variables ?med))
    (diagnostico (medida ?p&:(= ?p ?med))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function

(deffunction calcular-edad-gestacional (?medida)
(if (>= ?medida 2) then
        (bind ?it (run-query obtener-edad-gestacional ?medida))
        (while (?it hasNext)
    		(bind ?token (call ?it next))
    		(bind ?fact (call ?token fact 1))
    		(bind ?name (fact-slot-value ?fact edadGestacional))
    (return ?name))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Facts


 (deffacts tabla-biometria-fetal
     (diagnostico  (medida 2) (edadGestacional 5.0))
     (diagnostico  (medida 3) (edadGestacional 5.1))
     (diagnostico  (medida 4) (edadGestacional 5.2))
     (diagnostico  (medida 5) (edadGestacional 5.4))
     (diagnostico  (medida 6) (edadGestacional 5.5))
     (diagnostico  (medida 7) (edadGestacional 5.6))
     (diagnostico  (medida 8) (edadGestacional 5.7))
     (diagnostico  (medida 9) (edadGestacional 5.9))
     (diagnostico  (medida 10) (edadGestacional 6.0))
     (diagnostico  (medida 11) (edadGestacional 6.1))
     (diagnostico  (medida 12) (edadGestacional 6.2))
     (diagnostico  (medida 13) (edadGestacional 6.4))
     (diagnostico  (medida 14) (edadGestacional 6.5))
     (diagnostico  (medida 15) (edadGestacional 6.6))
     (diagnostico  (medida 16) (edadGestacional 6.7))
     (diagnostico  (medida 17) (edadGestacional 6.9))
     (diagnostico  (medida 18) (edadGestacional 7.0))
     (diagnostico  (medida 19) (edadGestacional 7.1))
     (diagnostico  (medida 20) (edadGestacional 7.3))
     (diagnostico  (medida 21) (edadGestacional 7.4))
     (diagnostico  (medida 22) (edadGestacional 7.5))
     (diagnostico  (medida 23) (edadGestacional 7.6))
     (diagnostico  (medida 24) (edadGestacional 7.8))
        )
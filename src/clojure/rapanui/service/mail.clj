(ns rapanui.service.mail
  (:import
    [java.util Properties]
    [javax.mail Message
                MessagingException
                Session
                Transport]
    [javax.mail.internet AddressException
                         InternetAddress
                         MimeMessage])
  (:require
    ;[rapanui.common :as COM]
    [rapanui.service.logservice :as LOG]))

(def ^:dynamic *email-option-sale*)

(defn send-email [subject msg-body]
  (if (= *email-option-sale* true)
    (let [props (Properties.)]
      (.put props "mail.smtp.host" "mail.broadpark.no")
      (let [session (Session/getDefaultInstance props nil)
            msg (MimeMessage. session)]
        (doto msg
          (.setFrom (InternetAddress. "hamilton108@broadpark.no" "Option Sales Manager"))
          (.addRecipient
            javax.mail.Message$RecipientType/TO
            (InternetAddress. "hamilton108@gmail.com" "Dude"))
          (.setSubject subject)
          (.setText msg-body))
        (try
          (Transport/send msg)
          (catch AddressException e1 (LOG/error (.getMessage e1)))
          (catch MessagingException e2 (LOG/error (.getMessage e2))))))))


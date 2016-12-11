(ns cqrs-framework.seed)

(def data
  [{:type        :task
    :description "Buy milk"
    :complete    false}
   {:type        :task
    :description "Eat curry"
    :complete    false}
   {:db/ident :app
    :new-task "Pat kittens"}])

(ns employees.core
  (:require
   [reagent.core :as r])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-rg defcard-doc deftest]]))

(enable-console-print!)

(defcard-doc
  #_"# Hello World!"

  "# Programação Interativa com ClojureScript"

  "Outline da palestra:

- Montar um exemplo interativamente
- O exemplo escolhido foi uma interface de filtrar funcionários
- Sem Todo MVC, por favor :P
- Teremos uma imagem de como deve ser o aplicativo (uma screenshot do exemplo final :P)
- Vamos começar com cada componente básico
  1. campo de busca
  1. componente do funcionário. com avatar e nome
  1. componente para listar funcionários
  1. como seria o estado da nossa aplicação? (:query, :employees)
  1. criar o componente que injeta a query no campo de busca
  1. fazer o campo de busca receber callback e exportar edições
  1. listar os funcionários a partir do estado
  1. implementar função que filtra os funcionários de acordo com a busca
  1. implementar test básico para o filtro
  1. usar o :inspect-data e :history para vermos como estamos utilizando os dados
- Vamos brincar no REPL. Podemos executar comandos para alterar o nosso estado.
- Vimos que é possível criar um aplicativo de dentro para fora, interativamente
- Isso possibilita um feedback rápido na hora do desenvolvimento
- Para o nosso aplicativo na Xerpa, temos também a vantagem de criar uma documentação viva para todos os componentes da interface, além de poder testá-los e brincar com o estado que renderiza cada um")

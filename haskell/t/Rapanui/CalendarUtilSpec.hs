
module Rapanui.Critters.CalendarUtilSpec
  ( spec
  )
where

import Test.Hspec

import Data.Time.LocalTime 
  ( TimeOfDay(..)
  )

import Rapanui.CalendarUtil

spec :: Spec
spec = do
  describe "CalendarUtilSpec" $ do
    it "9:12 should give correct TimeOfDay" $ do
      let actual = strToTimeOfDay "9:12"
      shouldBe actual (TimeOfDay 9 12 0)
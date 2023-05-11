module Rapanui.Critters.AcceptRuleSpec
  ( spec
  )
where

import Rapanui.Common
import Rapanui.Critters.AcceptRule
import Rapanui.Critters.OptionSale
import Rapanui.StockOption

import Test.Hspec

s1 :: StockOption
s1 = createStockOption (Buy 9.0) (Sell 11.0)

t1 :: OptionTicker
t1 = (OptionTicker "demo")

acc1 :: Double -> AcceptRule
acc1 v =
  AcceptRule
    { oid = (Oid 1)
    , pid = (Pid 23)
    , cid = (Cid 47)
    , rtyp = (Rtyp 7)
    , value = v
    , active = True
    }

spec :: Spec
spec = do
  describe "AcceptRuleSpec" $ do
    context "apply'" $ do
      let
        actual' = apply' (Sell 12.0) s1 (Rtyp 7)
      it "Should be Sale" $ do
        let
          actual = actual' (Buy 2.5)
        shouldBe actual (Sale (SalePayload t1 (Buy 9.0)))
      it "Should be NoSale" $ do
        let
          actual = actual' (Buy 5.0)
        shouldBe actual NoSale
    context "apply" $ do
      it "acc1 2.0 should be Sale" $ do
        let
          actual = apply (Sell 12.0) s1 (acc1 2.0)
        shouldBe actual (Sale (SalePayload t1 (Buy 9.0)))
    context "apply" $ do
      it "acc1 3.5 should be NoSale" $ do
        let
          actual = apply (Sell 12.0) s1 (acc1 3.5)
        shouldBe actual NoSale

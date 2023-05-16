module Rapanui.Critters.CritterSpec
  ( spec
  )
where

import Rapanui.Common
import Rapanui.Critters.AcceptRule
import Rapanui.Critters.Critter
import Rapanui.OptionSale.OptionSaleItem
import Rapanui.StockOption

import Test.Hspec

s1 :: StockOption
s1 = createStockOption (Buy 9.0) (Sell 11.0)

c1 :: Critter
c1 =
  Critter
    (Oid 45)
    7
    10
    [ AcceptRule (Oid 72) (Pid 47) (Cid 45) (Rtyp 7) 3.0 True
    ]

sale1 :: OptionSale
sale1 =
  Sale (SalePayload (Cid 45) (Buy 9.0))

spec :: Spec
spec = do
  describe "CritterSpec" $ do
    it "extractSale should be Sale" $ do
      let
        sales = [NoSale, NotActive, sale1]
      let
        actual = extractSale sales
      shouldBe actual sale1
    it "extractSale should be NoSale" $ do
      let
        sales = [NotActive, NoSale, NotActive, NoSale]
      let
        actual = extractSale sales
      shouldBe actual NoSale
    it "applyCritter should be Sale (sale1)" $ do
      let
        actual = applyCritter (Sell 12.01) s1 c1
      shouldBe actual sale1
    it "applyCritter should be NoSale" $ do
      let
        actual = applyCritter (Sell 11.95) s1 c1
      shouldBe actual NoSale

module Rapanui.Critters.OptionPurchaseSpec
  ( spec
  )
where

import Rapanui.Common
import qualified Rapanui.Critters.AcceptRule as A
import qualified Rapanui.Critters.Critter as C
import qualified Rapanui.Critters.OptionPurchase as O
import qualified Rapanui.OptionSale.OptionSaleItem as OS
import qualified Rapanui.StockOption as S

import Test.Hspec

p1 :: O.OptionPurchase
p1 =
  O.OptionPurchase
    (Oid 47)
    (OptionTicker "NHY9E30")
    (Ask 12.0)
    [ C.Critter
        (Oid 45)
        7
        10
        [ A.AcceptRule (Oid 72) (Pid 47) (Cid 45) (Rtyp 7) 3.0 True
        ]
    ]

p2 :: O.OptionPurchase
p2 =
  O.OptionPurchase
    (Oid 47)
    (OptionTicker "NHY9E30")
    (Ask 12.0)
    [ C.Critter
        (Oid 45)
        8
        10
        [ A.AcceptRule (Oid 72) (Pid 47) (Cid 45) (Rtyp 7) 3.0 True
        ]
    ]

p3 :: O.OptionPurchase
p3 =
  O.OptionPurchase
    (Oid 47)
    (OptionTicker "NHY9E30")
    (Ask 12.0)
    [ C.Critter
        (Oid 45)
        7
        10
        [ A.AcceptRule (Oid 72) (Pid 47) (Cid 45) (Rtyp 7) 3.0 False
        ]
    ]

s1 :: Bid -> Ask -> S.StockOption
s1 bid ask =
  S.createStockOption bid ask

spec :: Spec
spec = do
  describe "OptionPurchaseSpec" $ do
    it "should pass" $ do
      let
        actual = O.applyPurchase p1 (s1 (Bid 8.9) (Ask 9.0))
      shouldBe actual [OS.Sale (OS.SalePayload (Cid 45) (Bid 8.9))]
    it "should not pass due to critter being inactive (status = 8)" $ do
      let
        actual = O.applyPurchase p2 (s1 (Bid 8.9) (Ask 9.0))
      shouldBe actual [OS.NotActive]
    it "should not pass due to accept rule being inactive" $ do
      let
        actual = O.applyPurchase p3 (s1 (Bid 8.9) (Ask 9.0))
      shouldBe actual [OS.NoSale]

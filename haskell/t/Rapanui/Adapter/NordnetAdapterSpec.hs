module Rapanui.Adapter.NordnetAdapterSpec
  ( spec
  )
where

import qualified Data.ByteString.Lazy.Char8 as BL

-- import qualified Data.ByteString as BL
import Rapanui.Adapter.NordnetAdapter
import Rapanui.Common
import qualified Rapanui.Critters.AcceptRule as A
import qualified Rapanui.Critters.Critter as C
import qualified Rapanui.Critters.OptionPurchase as O
import Test.Hspec

critters :: BL.ByteString
critters =
  BL.pack
    "[{ \"ticker\": \"NHY9E30\",\
    \\"oid\": 47,\
    \\"price\": 12.0,\
    \\"critters\":\
    \[{\"vol\": 10,\
    \\"accRules\": \
    \[{\"value\": 3.0,\
    \\"active\": true,\
    \\"cid\": 45,\
    \\"rtyp\": 1,\
    \\"dnyRules\": null,\
    \\"oid\": 72,\
    \\"pid\": 47}],\
    \\"status\": 7, \
    \\"oid\": 45}]}]"

-- critters :: BL.ByteString
-- critters = BL.pack "[{ \"ticker2\": \"NHY9E30\", \"critters2\": [\"accRules2\": [{\"value\": 3.0,\"active\":true}]]}]"

expected :: [O.OptionPurchase]
expected =
  [ O.OptionPurchase
      (Oid 47)
      (OptionTicker "NHY9E30")
      (Ask 12.0)
      [ C.Critter
          (Oid 45)
          7
          10
          [ A.AcceptRule (Oid 72) (Pid 47) (Cid 45) (Rtyp 1) 3.0 True
          ]
      ]
  ]

-- expected :: Maybe Critter2
-- expected = Just $ Critter2 (OptionTicker "NHY9E30") [ AcceptRule 3.0 True ]

spec :: Spec
spec = do
  describe "NordnetAdapterSpec" $ do
    context "json" $ do
      it "adapter parses content ok" $ do
        let
          actual = parseCrittersJson $ BL.toStrict critters
        shouldBe actual expected

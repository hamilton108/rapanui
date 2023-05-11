{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Adapter.NordnetAdapter where

import Control.Monad.IO.Class (liftIO)
import Control.Monad.Reader
  ( MonadIO
  , MonadReader
  )
import qualified Control.Monad.Reader as Reader
import qualified Data.Aeson as Aeson

-- import qualified Data.ByteString.Lazy.Char8 as BL
import qualified Data.ByteString as BL
import Network.HTTP.Req
  ( HttpException (..)
  , (/:)
  )
import qualified Network.HTTP.Req as R
import Rapanui.Common
  ( CritterType (..)
  , Env
  , NordnetHost (..)
  , NordnetPort (..)
  , OptionTicker (..)
  )
import qualified Rapanui.Common as Common
import Rapanui.Critters.OptionPurchase (OptionPurchase)
import Rapanui.StockOption (StockOption)

crittersGET :: (MonadReader Env m) => m (R.Req R.BsResponse)
crittersGET =
  Reader.ask >>= \env ->
    let
      NordnetHost host = Common.getHost env
      NordnetPort port = Common.getPort env
      CritterType ct = Common.getCt env
      myUrl = R.http host /: "critters" /: ct
    in
      pure $ R.req R.GET myUrl R.NoReqBody R.bsResponse $ R.port port

parseCrittersJson :: BL.ByteString -> [OptionPurchase]
parseCrittersJson s =
  maybe [] id $ Aeson.decodeStrict s

fetchCritters :: (MonadIO m, MonadReader Env m) => m [OptionPurchase]
fetchCritters =
  crittersGET >>= \s ->
    R.runReq R.defaultHttpConfig s >>= \response ->
      pure $ parseCrittersJson $ R.responseBody response

stockOptionGET :: (MonadReader Env m) => OptionTicker -> m (R.Req R.BsResponse)
stockOptionGET (OptionTicker ticker) =
  Reader.ask >>= \env ->
    let
      NordnetHost host = Common.getHost env
      NordnetPort port = Common.getPort env
      myUrl = R.http host /: "option" /: ticker
    in
      pure $ R.req R.GET myUrl R.NoReqBody R.bsResponse $ R.port port

parseStockOptionJson :: BL.ByteString -> Maybe StockOption
parseStockOptionJson s =
  maybe Nothing id $ Aeson.decodeStrict s

fetchStockOption :: (MonadIO m, MonadReader Env m) => OptionTicker -> m (Maybe StockOption)
fetchStockOption ticker =
  stockOptionGET ticker >>= \s ->
    R.runReq R.defaultHttpConfig s >>= \response ->
      pure $ parseStockOptionJson $ R.responseBody response

-- demo :: IO (Maybe [OptionPurchase])
-- demo =
--   R.runReq R.defaultHttpConfig crittersGET >>= \result ->
--     pure $ parseCrittersJson (R.responseBody result)

-- demo2 :: IO R.BsResponse
-- demo2 =
--   R.runReq
--     R.defaultHttpConfig
--     (Reader.runReader crittersGET Common.demoEnv)

-- demo3' :: (MonadIO m, MonadReader Env m) => m Int
-- demo3' =
--   pure 10

demo3 :: IO [OptionPurchase]
demo3 =
  Reader.runReaderT fetchCritters Common.demoEnv

demo4 :: IO (Maybe StockOption)
demo4 =
  Reader.runReaderT (fetchStockOption $ OptionTicker "YAR3A528.02X") Common.demoEnv

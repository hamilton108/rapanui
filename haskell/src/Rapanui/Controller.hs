{-# LANGUAGE NamedFieldPuns #-}
{-# LANGUAGE RecordWildCards #-}
{-# LANGUAGE FlexibleContexts #-}

module Rapanui.Controller where


import Control.Monad.IO.Class
  ( liftIO
  )
import Control.Monad.Reader
  ( MonadIO
  , MonadReader
  , ask
  , runReaderT
  )
import qualified Rapanui.Adapter.NordnetAdapter as Adapter
import Rapanui.Common
  ( Env
  )
import Rapanui.Critters.OptionPurchase
  ( OptionPurchase (..)
  )
import qualified Rapanui.Critters.OptionPurchase as Purchase
import Rapanui.OptionSale.OptionSaleItem
  ( OptionSale (..)
  )
import Rapanui.OptionSale.OptionSaleService as Service

applyPurchase :: (MonadIO m, MonadReader Env m) => OptionPurchase -> m [OptionSale]
applyPurchase p@OptionPurchase{ticker} =
  Adapter.fetchStockOption ticker >>= \stockOption ->
    case stockOption of
      Nothing ->
        pure [NoSale]
      Just stockOption1 ->
        pure $ Purchase.applyPurchase p stockOption1

run :: (MonadIO m, MonadReader Env m) => m ()
run =
  liftIO (putStrLn "run...")

 
  -- Adapter.fetchCritters >>= \purchases ->
  --   mapM applyPurchase purchases >>= \sales ->
  --     Service.processSales $ concat sales

-- runReaderT (runApp applyPurchase $ head purchases) env  >>= \sales ->

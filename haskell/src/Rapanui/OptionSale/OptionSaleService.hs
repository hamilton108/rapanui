{-# LANGUAGE FlexibleContexts #-}

module Rapanui.OptionSale.OptionSaleService where

import Control.Monad.Reader
  ( MonadIO
  , MonadReader
  )
import Rapanui.Common
  ( Env
  )
import Rapanui.OptionSale.OptionSaleItem
  ( OptionSale (..)
  )
import qualified Rapanui.Adapter.NordnetAdapter as Adapter

processSales :: (MonadIO m, MonadReader Env m) => [OptionSale] -> m ()
processSales sales = 
  pure ()

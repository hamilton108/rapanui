import qualified Rapanui.CalendarUtil as Util
import qualified Rapanui.Controller as Controller
import qualified Rapanui.Params as PA

import Control.Concurrent
  ( threadDelay
  )
import Control.Monad
  ( forever
  , mzero
  , when
  )
import Control.Monad.IO.Class
  ( liftIO
  )
import Control.Monad.Reader
  ( runReaderT
  )
import Control.Monad.Trans
  ( lift
  )
import Control.Monad.Trans.Maybe
  ( runMaybeT
  )

-- import Data.Time.LocalTime
--   ( TimeOfDay
--   )

import Rapanui.Common
  ( Env
  , MarketClose (..)
  , MarketOpen (..)
  )
import qualified Rapanui.Common as Common

exit = mzero

whileMarketClosed :: MarketOpen -> IO (Maybe a)
whileMarketClosed (MarketOpen opn) =
  runMaybeT $
    forever $
      ( liftIO $
          putStrLn "MARKET IS STILL CLOSED!"
            >> threadDelay (PA.seconds2micro 5)
            >> Util.timeOfDayPassed opn
      )
        >>= \result ->
          when (result == True) exit

whileMarketOpen :: Env -> IO (Maybe a)
whileMarketOpen env =
  let 
    (MarketClose cls) = Common.getClose env
    interval = Common.getInterval env
  in
  runMaybeT $
    forever $
      ( liftIO $
          runReaderT (Common.runApp Controller.run) env >>
          threadDelay interval
            >> Util.timeOfDayPassed cls
      )
        >>= \result ->
          when (result == True) exit

-- demo :: IO (Maybe a)
-- demo =
--   whileMarketClosed (Common.getOpen Common.demoEnv)

-- main :: IO (Maybe a)
-- main = runMaybeT $ forever $ do
--   str <- lift getLine
--   when (str == "exit") exit

main :: IO ()
main =
  PA.cmdLineParser
    >>= \prm ->
      let
        env = PA.params2env prm
      in
        putStrLn (show prm)
          >> putStrLn (show env)
          -- >> whileMarketClosed (Common.getOpen env)
          >> whileMarketOpen env
          >> pure ()          

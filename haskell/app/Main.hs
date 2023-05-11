{-# LANGUAGE NamedFieldPuns,RecordWildCards,CPP #-}

-- #define RCS_DEMO
import Control.Monad (forever)
import Control.Concurrent (threadDelay)

import Rapanui.Params ( Params ) 
import qualified Rapanui.Params as PA
-- import Rapanui.Common 
--   ( NordnetHost(..)
--   , NordnetPort(..)
--   , CritterType(..)
--   , Env
--   )



main :: IO ()
main = PA.cmdLineParser
  >>= \prm -> 
      work prm 

-- applyPurchases :: (MonadIO m, MonadReader Env m) => m [OptionSale]
-- applyPurchases =
--   undefined
--concat $ map applyPurchase opx 

work :: Params -> IO ()
work params = 
  let 
    env = PA.params2env params
  in
    putStrLn (show params) >>
    putStrLn (show env) 

-- main :: IO ()
-- main = 
--   forever $ 
--     threadDelay (micro2min 1) >>
--     putStrLn "I DO UNDERSTAND!" 
  
{-
import System.Environment (getArgs)
import qualified Text.XML.Light as X 
import qualified StearnsWharf.System as S

#ifdef RCS_DEMO
import qualified StearnsWharf.XML.XmlLoads as XL
import qualified StearnsWharf.XML.XmlProfiles as XP
#endif 

main :: IO ()
main = do
#ifdef RCS_DEMO
  --s <- readFile "/home/rcs/opt/haskell/stearnswharf/demo/project2.xml"
  s <- readFile "/home/rcs/opt/haskell/stearnswharf/t/distload05.xml"
#else
  [fileName] <- getArgs
  s <- readFile fileName 
#endif
  case X.parseXMLDoc s of
    Nothing -> error "Failed to parse xml"
    Just doc -> S.runStearnsWharf doc


#ifdef RCS_DEMO

crl = XL.createLoads

cwp = XP.createWoodProfiles

demo :: IO X.Element
demo = do
  s <- readFile "/home/rcs/opt/myProjects3/Odins_vei_21/xml/demo.xml"
  case X.parseXMLDoc s of
    Nothing -> error "Failed to parse xml"
    Just doc -> pure doc
#endif
-}
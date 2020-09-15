package fr.nathanael2611.modernvoicecontent.block;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.proxy.ClientProxy;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockRadio extends BlockSpeaker
{
    public BlockRadio()
    {
        super("radioblock");
        this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATE, false).withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVATE, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();
        if(meta == 0)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(ACTIVATE, true);
        }
        if(meta == 1)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(ACTIVATE, false);
        }
        if(meta == 2)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.EAST).withProperty(ACTIVATE, true);
        }
        if(meta == 3)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.EAST).withProperty(ACTIVATE, false);
        }
        if(meta == 4)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.WEST).withProperty(ACTIVATE, true);
        }
        if(meta == 5)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.WEST).withProperty(ACTIVATE, false);
        }
        if(meta == 6)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(ACTIVATE, true);
        }
        if(meta == 7)
        {
            return state.withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(ACTIVATE, false);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        boolean activate = state.getValue(ACTIVATE);
        EnumFacing facing = state.getValue(BlockHorizontal.FACING);
        if(facing == EnumFacing.NORTH && activate)
        {
            return 0;
        }
        if(facing == EnumFacing.NORTH)
        {
            return 1;
        }

        if(facing == EnumFacing.EAST && activate)
        {
            return 2;
        }
        if(facing == EnumFacing.EAST)
        {
            return 3;
        }

        if(facing == EnumFacing.WEST && activate)
        {
            return 4;
        }
        if(facing == EnumFacing.WEST)
        {
            return 5;
        }

        if(facing == EnumFacing.SOUTH && activate)
        {
            return 6;
        }
        if(facing == EnumFacing.SOUTH)
        {
            return 7;
        }

        return 0;
    }
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(BlockHorizontal.FACING, rot.rotate((EnumFacing)state.getValue(BlockHorizontal.FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BlockHorizontal.FACING, BlockSpeaker.ACTIVATE});
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
        {
            if (playerIn.isSneaking())
            {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te instanceof TileEntityRadio)
                {
                    ClientProxy.openRadioGui((TileEntityRadio) te);
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityRadio();
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}

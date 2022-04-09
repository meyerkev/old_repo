xof 0303txt 0032

// DirectX - from MilkShape3D

Frame skybox1Frame
{
    FrameTransformMatrix
    {
        1.000000,0.000000,0.000000,0.000000,
        0.000000,1.000000,0.000000,0.000000,
        0.000000,0.000000,1.000000,0.000000,
        0.000000,0.000000,0.000000,1.000000;;
    }

    Mesh skybox1Mesh
    {
        24;
        -10.000000;-10.000000;-10.000000;,
        -10.000000;-10.000000;10.000000;,
        10.000000;-10.000000;10.000000;,
        10.000000;-10.000000;-10.000000;,
        -10.000000;10.000000;10.000000;,
        -10.000000;10.000000;-10.000000;,
        10.000000;10.000000;-10.000000;,
        10.000000;10.000000;10.000000;,
        -10.000000;-10.000000;-10.000000;,
        -10.000000;10.000000;-10.000000;,
        -10.000000;10.000000;10.000000;,
        -10.000000;-10.000000;10.000000;,
        10.000000;-10.000000;10.000000;,
        10.000000;10.000000;10.000000;,
        10.000000;10.000000;-10.000000;,
        10.000000;-10.000000;-10.000000;,
        -10.000000;-10.000000;10.000000;,
        -10.000000;10.000000;10.000000;,
        10.000000;10.000000;10.000000;,
        10.000000;-10.000000;10.000000;,
        10.000000;-10.000000;-10.000000;,
        10.000000;10.000000;-10.000000;,
        -10.000000;10.000000;-10.000000;,
        -10.000000;-10.000000;-10.000000;;
        12;
        3;0,1,2;,
        3;3,0,2;,
        3;4,5,6;,
        3;7,4,6;,
        3;8,9,10;,
        3;11,8,10;,
        3;12,13,14;,
        3;15,12,14;,
        3;16,17,18;,
        3;19,16,18;,
        3;20,21,22;,
        3;23,20,22;;

        MeshNormals
        {
            6;
            0.000000;1.000000;0.000000;,
            0.000000;-1.000000;0.000000;,
            1.000000;0.000000;0.000000;,
            -1.000000;0.000000;0.000000;,
            0.000000;0.000000;-1.000000;,
            0.000000;0.000000;1.000000;;
            12;
            3;0,0,0;,
            3;0,0,0;,
            3;1,1,1;,
            3;1,1,1;,
            3;2,2,2;,
            3;2,2,2;,
            3;3,3,3;,
            3;3,3,3;,
            3;4,4,4;,
            3;4,4,4;,
            3;5,5,5;,
            3;5,5,5;;
        }

        MeshTextureCoords
        {
            24;
            0.000000;0.000000;,
            0.000000;1.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            0.000000;0.000000;,
            0.000000;1.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            0.000000;0.000000;,
            0.000000;1.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            0.000000;0.000000;,
            0.000000;1.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            0.000000;0.000000;,
            0.000000;1.000000;,
            1.000000;1.000000;,
            1.000000;0.000000;,
            0.000000;0.000000;,
            0.000000;1.000000;;
        }

        MeshMaterialList
        {
            6;
            12;
            0,
            0,
            1,
            1,
            2,
            2,
            3,
            3,
            4,
            4,
            5,
            5;

            Material down
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                1.000000;1.000000;1.000000;;

                TextureFileName
                {
                    "sk1down.bmp";
                }
            }

            Material up
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                1.000000;1.000000;1.000000;;

                TextureFileName
                {
                    "sk1up.bmp";
                }
            }

            Material left
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                1.000000;1.000000;1.000000;;

                TextureFileName
                {
                    "sk1left.bmp";
                }
            }

            Material right
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                1.000000;1.000000;1.000000;;

                TextureFileName
                {
                    "sk1right.bmp";
                }
            }

            Material back
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                1.000000;1.000000;1.000000;;

                TextureFileName
                {
                    "sk1back.bmp";
                }
            }

            Material front
            {
                1.000000;1.000000;1.000000;1.000000;;
                128.000000;
                0.000000;0.000000;0.000000;;
                0.988235;0.988235;0.988235;;

                TextureFileName
                {
                    "sk1front.bmp";
                }
            }
        }
    }
}